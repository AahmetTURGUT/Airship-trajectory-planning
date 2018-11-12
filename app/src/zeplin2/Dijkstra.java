/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeplin2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Math.toDegrees;
import java.net.URL;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Vertex implements Comparable<Vertex> {

    public final String name;
    public ArrayList<Edge> adjacencies = new ArrayList<Edge>();
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public double latitude, longitude;
    public int rakim;

    public Vertex(String argName) {
        name = argName;
    }

    public String toString() {
        return name;
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

}

class Edge {

    public Vertex target;
    public double weight;

    public Edge(Vertex argTarget, double argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}

public class Dijkstra {

    public static void computePaths(Vertex source) { 
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        ArrayList<Vertex> vertexQueue2 = new ArrayList<Vertex>();

        vertexQueue.add(source);
        vertexQueue2.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

           
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                

                if (distanceThroughU < v.minDistance) {
                  
                    vertexQueue.remove(v);
                    vertexQueue2.remove(v);

                    v.minDistance = distanceThroughU;
                    v.previous = u;
                   
                    vertexQueue.add(v);
                    vertexQueue2.add(v);

                   
                    for (int i = 0; i < vertexQueue.size(); i++) {
                     
                    }
                

                }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }

    private static double aciHesap(double mesafe, int rakim1, int rakim2) {

        int yukseklik = rakim2 - rakim1 + 50;

        double aci = toDegrees(Math.atan(yukseklik / mesafe));
        if(aci<0){
        return (aci*(-1));
        }else{
         return aci;
        }
       

    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }


    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

   
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Scanner input = new Scanner(System.in);
        int bas, bit;
        System.out.print("Baslangıc sehrinin plaka kodunu giriniz: ");
        bas = input.nextInt();
        System.out.print("Bitis sehrinin plaka kodunu giriniz: ");
        bit = input.nextInt();
        System.out.println("");
        System.out.print("Sabit icin 1: %50 kar için 2 ye bas: ");
        int secim = input.nextInt();
        if (secim == 1) {
            for (int yolcusayisi = 5; yolcusayisi < 50; yolcusayisi++) {
                Timer.start();
                int maxAci = 80 - yolcusayisi;

                FileReader in = new FileReader("Komşuluklar.txt");
                BufferedReader br = new BufferedReader(in);

                ArrayList<Vertex> citys = new ArrayList<Vertex>();

                String line;
                while ((line = br.readLine()) != null) {
                    String parts[] = line.split(",");

                    Vertex v = new Vertex(parts[0]);
                    citys.add(v);

                    
                }

             
             

                in = new FileReader("lat long.txt");
                br = new BufferedReader(in);

                int k = 0;
                while ((line = br.readLine()) != null) {
                    String parts[] = line.split(",");

                    citys.get(k).latitude = Double.parseDouble(parts[0]);
                    citys.get(k).longitude = Double.parseDouble(parts[1]);
                    citys.get(k).rakim = Integer.parseInt(parts[3]);

                    k++;
                }

            
                FileReader in2 = new FileReader("Komşuluklar.txt");
                BufferedReader br2 = new BufferedReader(in2);

                int i = 0;

                while ((line = br2.readLine()) != null) {

                   
                    String parts[] = line.split(",");

                    for (int j = 1; j < parts.length; j++) {
                        for (k = 0; k < citys.size(); k++) {
                            if (parts[j].equals(citys.get(k).name)) {
                               

                                
                               double distancee = distance(citys.get(i).latitude, citys.get(i).longitude, citys.get(k).latitude, citys.get(k).longitude);
                               
                                 
                               if (aciHesap(distance(citys.get(i).latitude, citys.get(i).longitude, citys.get(k).latitude, citys.get(k).longitude), citys.get(i).rakim, citys.get(k).rakim) > maxAci) {
                                  

                                } else {
                                    
                                       double t=Math.sqrt((distancee*distancee)+ (citys.get(k).rakim-citys.get(i).rakim+50) * (citys.get(k).rakim-citys.get(i).rakim+50)      );
                                       
                                    citys.get(i).adjacencies.add(new Edge(citys.get(k), t));
                                 

                                     String yazma= (citys.get(i).name+" sehrinden "+parts[j]+" sehrine kusbakisi mesafe="+distancee+" acili mesafe :"+t+"\n" );
                                     Yaz.DosyayaEkle(yazma);
                                        
                                }
                            }
                        }
                    }
                    i++;
                }

                ArrayList<Double> scoresx = new ArrayList<Double>();
                ArrayList<Double> scoresy = new ArrayList<Double>();
                for (i = 0; i < citys.size(); i++) {
                    scoresx.add(citys.get(i).latitude);
                    scoresy.add(citys.get(i).longitude);

                }

                computePaths(citys.get(bas - 1)); 
                double inf = Double.POSITIVE_INFINITY;
                List<Vertex> path = getShortestPathTo(citys.get(bit - 1));
                if (citys.get(bit - 1).minDistance != inf) {
                    System.out.println(citys.get(bit - 1) + ". sehre " + yolcusayisi + " kisi ile en kisa yolun km'si: " + citys.get(bit - 1).minDistance);

                    System.out.println("Path: " + path);

                    double bb = ((100 * yolcusayisi) / (citys.get(bit - 1).minDistance * 10) - 1) * 100;
                    System.out.println("100 TL bilet fiyatı ile %" + bb + " kar");
                    //System.out.println("");

                    String latLong = null;
                    for (i = 0; i < path.size(); i++) {
                        latLong += "|" + path.get(i).latitude + ",";
                        latLong += path.get(i).longitude;

                    }
                   
                    JFrame jFrame = new JFrame("Google Maps");

                    jFrame.setPreferredSize(new Dimension(640, 640));
                    jFrame.setMinimumSize(new Dimension(640, 640));
                    jFrame.setMaximumSize(new Dimension(1024, 1024));

                    try {
                        String latitude = "38";
                        String longitude = "35";
                        String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center="
                                + latitude
                                + ","
                                + longitude
                                + "&zoom=5&path=color:0xff0000ff|weight:5"
                                + latLong
                                + "&size=1024x640&scale=2&maptype=roadmap";

                        String destinationFile = "image.jpg";

                        URL url = new URL(imageUrl);
                        InputStream is = url.openStream();
                        OutputStream os = new FileOutputStream(destinationFile);
                        byte[] b = new byte[2048];
                        int length;
                        while ((length = is.read(b)) != -1) {
                            os.write(b, 0, length);
                        }
                        is.close();
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }


                    ImageIcon imageIcon = new ImageIcon((new ImageIcon("image.jpg"))
                            .getImage().getScaledInstance(630, 600,
                                    java.awt.Image.SCALE_SMOOTH));
                    jFrame.add(new JLabel(imageIcon));
                    jFrame.add(new PaintPane(imageIcon.getImage()));


                    jFrame.setVisible(true);
                    jFrame.pack();

                } else {
                    System.out.println(yolcusayisi + " kisi icin yol bulunamadı ");
                }
                Timer.stop();
                double seconds = Timer.getElapsedSeconds();
                System.out.println("Geçen süre " + seconds + " saniyedir");
                System.out.println("");
            }

        } else {
            for (int yolcusayisi = 10; yolcusayisi < 51; yolcusayisi += 10) {
                Timer.start();
                int maxAci = 80 - yolcusayisi;

                FileReader in = new FileReader("Komşuluklar.txt");
                BufferedReader br = new BufferedReader(in);

                ArrayList<Vertex> citys = new ArrayList<Vertex>();

                String line;
                while ((line = br.readLine()) != null) {
                    String parts[] = line.split(",");

                    Vertex v = new Vertex(parts[0]);
                    citys.add(v);

                   
                }

               
                for (int i = 0; i < citys.size(); i++) {
                   
                }

                in = new FileReader("lat long.txt");
                br = new BufferedReader(in);

                int k = 0;
                while ((line = br.readLine()) != null) {
                    String parts[] = line.split(",");

                    citys.get(k).latitude = Double.parseDouble(parts[0]);
                    citys.get(k).longitude = Double.parseDouble(parts[1]);
                    citys.get(k).rakim = Integer.parseInt(parts[3]);

                    k++;
                }

                
                FileReader in2 = new FileReader("Komşuluklar.txt");
                BufferedReader br2 = new BufferedReader(in2);

                int i = 0;

                while ((line = br2.readLine()) != null) {

                    
                    String parts[] = line.split(",");

                    for (int j = 1; j < parts.length; j++) {
                        for (k = 0; k < citys.size(); k++) {
                            if (parts[j].equals(citys.get(k).name)) {
                                

                                
                               double distancee = distance(citys.get(i).latitude, citys.get(i).longitude, citys.get(k).latitude, citys.get(k).longitude);
                               
                                 
                               if (aciHesap(distance(citys.get(i).latitude, citys.get(i).longitude, citys.get(k).latitude, citys.get(k).longitude), citys.get(i).rakim, citys.get(k).rakim) > maxAci) {

                                } else {
                                    
                                       double t=Math.sqrt((distancee*distancee)+ (citys.get(k).rakim-citys.get(i).rakim+50) * (citys.get(k).rakim-citys.get(i).rakim+50)      );
                                       
                                    citys.get(i).adjacencies.add(new Edge(citys.get(k), t));

                                }
                            }
                        }

                    }
                    i++;
                }

                ArrayList<Double> scoresx = new ArrayList<Double>();
                ArrayList<Double> scoresy = new ArrayList<Double>();
                for (i = 0; i < citys.size(); i++) {
                    scoresx.add(citys.get(i).latitude);
                    scoresy.add(citys.get(i).longitude);

                }

                computePaths(citys.get(bas - 1)); 
                double inf = Double.POSITIVE_INFINITY;
                List<Vertex> path = getShortestPathTo(citys.get(bit - 1));
                if (citys.get(bit - 1).minDistance != inf) {
                    System.out.println(citys.get(bit - 1) + ". sehre " + yolcusayisi + " kisi ile en kisa yolun km'si: " + citys.get(bit - 1).minDistance);

                    System.out.println("Path: " + path);

                    double aa = (150 * citys.get(bit - 1).minDistance) / (10 * yolcusayisi);
                    System.out.println("%50 kar icin bilet fiyatı: " + aa + " TL");

                    String latLong = null;
                    for (i = 0; i < path.size(); i++) {
                        latLong += "|" + path.get(i).latitude + ",";
                        latLong += path.get(i).longitude;

                    }
                   
                    JFrame jFrame = new JFrame("Google Maps");

                    jFrame.setPreferredSize(new Dimension(640, 640));
                    jFrame.setMinimumSize(new Dimension(640, 640));
                    jFrame.setMaximumSize(new Dimension(1024, 1024));

                    try {
                        String latitude = "38";
                        String longitude = "35";
                        String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center="
                                + latitude
                                + ","
                                + longitude
                                + "&zoom=5&path=color:0xff0000ff|weight:5"
                                + latLong
                                + "&size=1024x640&scale=2&maptype=roadmap";

                        String destinationFile = "image.jpg";

                        URL url = new URL(imageUrl);
                        InputStream is = url.openStream();
                        OutputStream os = new FileOutputStream(destinationFile);
                        byte[] b = new byte[2048];
                        int length;
                        while ((length = is.read(b)) != -1) {
                            os.write(b, 0, length);
                        }
                        is.close();
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }


                    ImageIcon imageIcon = new ImageIcon((new ImageIcon("image.jpg"))
                            .getImage().getScaledInstance(630, 600,
                                    java.awt.Image.SCALE_SMOOTH));
                    jFrame.add(new JLabel(imageIcon));
                    jFrame.add(new PaintPane(imageIcon.getImage()));


                    jFrame.setVisible(true);
                    jFrame.pack();

                } else {
                    System.out.println(yolcusayisi + " kisi icin yol bulunamadı ");
                }
                            Timer.stop();
                double seconds = Timer.getElapsedSeconds();
                System.out.println("Geçen süre " + seconds + " saniyedir");
                System.out.println("");
            }

        }

    }

    public static class PaintPane extends JPanel {

        Image image;

        PaintPane(Image image) {
            this.image = image;
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(image, 0, 0, this);
            g.drawLine(0, 0, 100, 100);
        }

    }

}
