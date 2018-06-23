package leftovers.service.stockpricepredictimpl;

import leftovers.repository.StockRepository;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hiki on 2017/6/4.
 */

@Component
public class StockPricePredictor {

    // 神经网络相关配置
    NeuralNetwork<BackPropagation> neuralNetwork;
    private final static int INPUT_SIZE = 5;
    private final static LocalDate TRAINING_DATE = LocalDate.of(2005, 1, 1);

    // 数据配置
    @Autowired
    private StockRepository stockRepository;
    private List<Double> prices;
    private double maxPrice;
    private double minPrice;

    // 阈值常量
    private final static long STOCK_THRESHOLD = 200;

    // 初始化
    public void initialize(String symbol){
        neuralNetwork = new MultiLayerPerceptron(INPUT_SIZE, 2 * INPUT_SIZE + 1, 1);
        if (this.prices != null)
            this.prices.clear();
        this.prices = this.stockRepository.findCloseByPkCodeAndPkDateAfter(symbol, TRAINING_DATE).stream().filter(e -> e != null).collect(Collectors.toList());
        this.maxPrice = prices.stream().mapToDouble(Double::valueOf).max().orElse(0);
        this.minPrice = prices.stream().mapToDouble(Double::valueOf).min().orElse(0);
    }

    // 产生用于训练的数据集
    private DataSet produceTrainingData(){

        // 先定义要训练的数据集并初始化
        DataSet trainingSet = new DataSet(INPUT_SIZE, 1);

        // 将原始数据集加载为可用于训练的数据，每INPUT_SIZE+1个数据为一组
        int offset = 0;
        while(prices.size() - offset >= INPUT_SIZE + 1) {
            // 提取出训练数据
            double[] trainValues = new double[INPUT_SIZE];
            for (int i = 0; i < INPUT_SIZE; i++){
                trainValues[i] = normalizePrice(prices.get(i+offset));
            }
            double[] expectedValue = new double[]{normalizePrice(prices.get(INPUT_SIZE+offset))};

            trainingSet.addRow(trainValues, expectedValue);
            offset++;
        }

        return trainingSet;
        
    }

    // 训练模型
    private void trainNetwork(){
        // 初始化神经网络及一些参数
        int maxIterations = 1000;
        double learningRate = 0.5;
        double maxError = 0.00001;
        SupervisedLearning learningRule = neuralNetwork.getLearningRule();
        learningRule.setMaxError(maxError);
        learningRule.setLearningRate(learningRate);
        learningRule.setMaxIterations(maxIterations);
        learningRule.addListener(learningEvent -> {
            SupervisedLearning rule = (SupervisedLearning) learningEvent.getSource();
        });

        // 开始训练
        DataSet trainingSet = produceTrainingData();
        neuralNetwork.learn(trainingSet);
    }

    // 根据模型预测下一个价格数据
    public double predictNextPrice(){
        // 进行数据准备并训练模型
        trainNetwork();
        // 取出prices后INPUT_SIZE个数据
        List<Double> inputs = new ArrayList<>();
        for (int i = 0; i < INPUT_SIZE; i++){
            inputs.add(normalizePrice(prices.get(prices.size() - INPUT_SIZE + i)));;
        }

        neuralNetwork.setInput(inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3), inputs.get(4));
        neuralNetwork.calculate();
        double output = neuralNetwork.getOutput()[0];

        return deNormalizePrice(output);

    }

    // 根据模型预测下M个价格数据
    public List<Double> predictNextMPrice(int m){
        // 进行数据准备并训练模型
        trainNetwork();
        // 取出prices后INPUT_SIZE个数据
        List<Double> inputs = new ArrayList<>();
        List<Double> outputs = new ArrayList<>();

        for (int i = 0; i < INPUT_SIZE; i++){
            inputs.add(normalizePrice(prices.get(prices.size() - INPUT_SIZE + i)));
        }

        for (int i = 0; i < m; i++) {
            neuralNetwork.setInput(inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3), inputs.get(4));
            neuralNetwork.calculate();
            double output = neuralNetwork.getOutput()[0];
            // 输入输出集处理
            inputs.add(output);
            inputs.remove(0);
            outputs.add(deNormalizePrice(output));
            System.out.println(outputs);
        }

        return outputs;

    }

    public List<Double> predictNP1Price(int n){
        // 进行数据准备并训练模型
        trainNetwork();
        // 取出prices后INPUT_SIZE个数据
        List<Double> inputs = new ArrayList<>();
        List<Double> outputs = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            // 准备输入
            inputs.clear();
            for (int j = 0; j < INPUT_SIZE; j++){
                inputs.add(normalizePrice(prices.get(prices.size() - (n-i) - INPUT_SIZE + j)));
            }

            neuralNetwork.setInput(inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3), inputs.get(4));
            neuralNetwork.calculate();
            double output = neuralNetwork.getOutput()[0];

            outputs.add(deNormalizePrice(output));

        }

        return outputs;


    }

    public boolean fitToPredict(){
        if (prices.size() < STOCK_THRESHOLD)
            return false;
        return true;
    }

    private double normalizePrice(double input){
        return (input - minPrice) / (maxPrice - minPrice) * 0.8 + 0.1;
    }

    private double deNormalizePrice(double input) {
        return minPrice + (input - 0.1) * (maxPrice - minPrice) / 0.8;
    }


}
