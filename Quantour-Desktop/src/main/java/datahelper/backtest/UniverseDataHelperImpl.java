package datahelper.backtest;

import datahelper.utilities.FileDeleter;
import datahelper.utilities.FileOpener;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import po.backtest.UniversePO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hiki on 2017/4/16.
 */
public class UniverseDataHelperImpl implements UniverseDataHelper {

    private void addOrUpdateUniverse(String universeName, List<String> universe, boolean add) throws DuplicateName {
        BufferedWriter bufferedWriter = add ? new FileOpener().openNewUniverseFileAsBW(universeName) : new FileOpener().openUniverseFileAsBW(universeName);

        try {
            for (String code : universe) {bufferedWriter.write(code + System.lineSeparator());}
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUniverse(String universeName, List<String> universe) throws DuplicateName {
        addOrUpdateUniverse(universeName, universe, true);
    }

    @Override
    public void delUniverse(String universeName) {
        new FileDeleter().deleteUniverseFile(universeName);
    }

    @Override
    public List<UniversePO> findAllUniverses() {
        List<UniversePO> res = new ArrayList<>();

        Arrays.stream(new FileOpener().openUniversesDir().list())
                .map(filename -> filename.replace(".txt", ""))
                .forEach(universeName -> res.add(find(universeName)));

        return res;
    }

    @Override
    public void updateUniverse(String universeName, List<String> universe) {
        try {
            addOrUpdateUniverse(universeName, universe, false);
        } catch (DuplicateName duplicateName) {
        }
    }

    public UniversePO find(String universeName){
        BufferedReader bufferedReader = new FileOpener().openUniverseFileAsBR(universeName);

        UniversePO res =  new UniversePO(universeName, bufferedReader.lines().collect(Collectors.toList()));

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

}
