package data_providers;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InvalidMailForAddContactTest_DP {
    @DataProvider
    public Iterator<String> dataProviderFromFile(){
        List<String> list = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(
                new FileReader("src/test/resources/data_csv/invalid_email_contacts.csv"))){
            String line = bufferedReader.readLine();
            while (line != null){
                list.add(line);
                line = bufferedReader.readLine();
            }
        }catch (IOException exception){
            exception.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }
}
