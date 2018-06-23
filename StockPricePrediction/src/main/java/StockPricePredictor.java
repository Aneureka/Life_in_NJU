import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hiki on 2017/6/3.
 */
public class StockPricePredictor {

    // 神经网络相关配置
    private int inputSize;
    private String neuralNetworkModelFilePath = "stockPredictor.nnet";
    NeuralNetwork<BackPropagation> neuralNetwork;

    // 数据配置
    private String symbol;
    private List<Double> prices;
    private double maxPrice;
    private double minPrice;


    public StockPricePredictor(String symbol) {
        this.symbol = symbol;
        initData(symbol);
        inputSize = 5;
        neuralNetwork = new MultiLayerPerceptron(inputSize, 2*inputSize+1, 1);
    }


    // 初始化数据
    private void initData(String symbol){
        DataProvider dp = new DataProvider();
        dp.loadData(symbol);
        prices = dp.getHistoryDatas().stream().collect(Collectors.toList());
        maxPrice = prices.stream().mapToDouble(Double::valueOf).max().orElse(0);
        minPrice = prices.stream().mapToDouble(Double::valueOf).min().orElse(0);
    }

    // 产生用于训练的数据集
    private DataSet loadTrainingData(){

        // 先定义要训练的数据集并初始化
        DataSet trainingSet = new DataSet(inputSize, 1);

        // 将原始数据集加载为可用于训练的数据，每inputSize+1个数据为一组
        int offset = 0;
        while(prices.size() - offset >= inputSize + 1) {
            // 提取出训练数据
            double[] trainValues = new double[inputSize];
//            System.out.print("input: ");
            for (int i = 0; i < inputSize; i++){
                trainValues[i] = normalizePrice(prices.get(i+offset));
//                System.out.print(trainValues[i] + " ");
            }
            double[] expectedValue = new double[]{normalizePrice(prices.get(inputSize+offset))};
//            System.out.print("output: ");
//            System.out.println(expectedValue[0]);
            trainingSet.addRow(trainValues, expectedValue);

            offset++;
        }

        return trainingSet;
    }

    // 训练数据
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
//            System.out.println("Network error for interation " + rule.getCurrentIteration() + ": " + rule.getTotalNetworkError());
        });

        // 开始训练
        DataSet trainingSet = loadTrainingData();
        neuralNetwork.learn(trainingSet);
        neuralNetwork.save(neuralNetworkModelFilePath);
    }

    // 预测下一个股票价格
    public void predictStockPrice(){
        // 进行数据准备并训练模型
        trainNetwork();
        // 取出prices后inputSize个数据
        double[] inputPrices = new double[inputSize];
        for (int i = 0; i < inputSize; i++){
            inputPrices[i] = normalizePrice(prices.get(prices.size()-inputSize+i));
        }

        neuralNetwork.setInput(inputPrices[0], inputPrices[1], inputPrices[2], inputPrices[3], inputPrices[4]);
        neuralNetwork.calculate();
        double[] output = neuralNetwork.getOutput();
        System.out.println("Expected Value: " + prices.get(prices.size()-1));
        System.out.println("Predicted Value: " + deNormalizePrice(output[0]));
        System.out.println("Input size: " + prices.size());

    }


    private double normalizePrice(double input){
        return (input - minPrice) / (maxPrice - minPrice) * 0.8 + 0.1;
    }

    private double deNormalizePrice(double input) {
        return minPrice + (input - 0.1) * (maxPrice - minPrice) / 0.8;
    }


    public static void main(String[] args) {
        StockPricePredictor sp = new StockPricePredictor("000001.SZ");
        System.out.println(sp.prices);
        sp.predictStockPrice();
    }


}
