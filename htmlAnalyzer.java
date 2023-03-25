import java.io.*;
import java.net.*;

//VINICIUS ALVES GOES

public class htmlAnalyzer {
    static int level = 0, counter = 0;
    static String text;
    
    public static void analyze(String line){
        //check if tag or text
        if (line.charAt(0) == '<'){
            //check if tag opens or closes
            if(line.charAt(1) == '/'){
                counter--;
            }
            else{            
                counter++;
                //if counter > level, it means there is a deeper level, as text or new level
                if (counter > level){
                    level = counter;
                }
            }
        }
        else{
            //if counter == level, it means the text found is the current max level
            if (counter == level){
                text = line;
            }
        }
    }

    public static void main(String[] args){
        
        //Handle exceptions, specifically MalformedURLException, IOException (inputBuffer) and malformed HTML (opcional feature)
        try{
            //Create URL object and open file in buffer 
            URL url = new URL(args[0]);
            BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            
            while ((line = inputBuffer.readLine()) != null){
                //eliminate whitespaces and blank lines, call function 
                line = line.trim();
                if (line.length() != 0){
                    analyze(line);
                }
            }

            //If counter != 0, it means the tags are not balanced (for example, there is an open tag and didn't close)
            if (counter != 0){
                throw new Exception("malformed HTML");
            }
            
            //Print out answer and close input buffer
            System.out.println(text);
            inputBuffer.close();
        } 
        catch (MalformedURLException e){
            System.out.println("URL connection error");
            return;
        }
        //Only to make sure that the input buffer exception is taken care of
        catch (IOException e){
            return;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
  
    } 
}
