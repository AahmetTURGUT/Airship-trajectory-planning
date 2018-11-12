/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeplin2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ahmet
 */
public class Yaz {
       static void DosyayaEkle(String metin){
            try{
                  File dosya = new File("Komsular-Mesafe.txt");
                  FileWriter yazici = new FileWriter(dosya,true);
                  BufferedWriter yaz = new BufferedWriter(yazici);
                  yaz.write(metin);
                  yaz.close();
                  
            }
            catch (IOException hata){
                  hata.printStackTrace();
            }
      }
}
