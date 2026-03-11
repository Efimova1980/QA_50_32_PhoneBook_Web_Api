package data_providers;

import dto.Contact;
import dto.User;
import org.testng.annotations.DataProvider;
import utils.UserFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class UserDataProvider {
    @DataProvider
    public Iterator<User> dataProviderFromFile_WrongPassword(){
        List<User> list = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(
                new FileReader("src/test/resources/data_csv/invalid_password_user.csv"))){
            String line = bufferedReader.readLine();
            while (line != null){
                User user = UserFactory.positiveUser();
                user.setPassword(line);
                list.add(user);
                line = bufferedReader.readLine();
            }
        }catch (IOException exception){
            throw new RuntimeException("IO exception" + exception.getMessage());
        }
        return list.listIterator();
    }

    @DataProvider
    public Iterator<User> dataProviderFromFile_WrongEmail(){
        List<User> list = new ArrayList<>();
        Random rn = new Random();

        try(BufferedReader bufferedReader = new BufferedReader(
                new FileReader("src/test/resources/data_csv/invalid_email_user.csv"))){
            String line = bufferedReader.readLine();
            while (line != null){
                User user = UserFactory.positiveUser();
                user.setUsername(rn.nextInt(1000) + line);
                list.add(user);
                line = bufferedReader.readLine();
            }
        }catch (IOException exception){
            throw new RuntimeException("IO exception" + exception.getMessage());
        }
        return list.listIterator();
    }


}
