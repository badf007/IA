package alphaplus.IA;

import java.net.SocketPermission;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.println;
import static spark.Spark.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by No. GP62 on 19/05/2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Document doc = null;
        Document docs = null;
        String url= null;
        String url_request = null;
        String metotodo;
        int flag = 0;

        while (flag == 0) {

            try {
             /*   print("Introduzca una URL valida: ");
                Scanner in = new Scanner(System.in);
                //int i = in.nextInt();
                url = in.next();*/
                //http://navaloan.com/
                url = "http://itachi.avathartech.com:4567/2017.html";
                url_request ="http://itachi.avathartech.com:4567";

                print("Fetching %s...", url);

                doc = Jsoup.connect(url).get();
                //docs = Jsoup.connect(url).execute().body();
                flag = 1;

            } catch (Exception e) {
                print("Introduce una URL valida. \n");
            }

        }

        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        Elements parrafos  = doc.select("p");
        Elements formularios = doc.select("form");
        Elements inputs = doc.select("input");


        String[] codigo = Jsoup.connect(url).execute().body().split("\n");
        /*
        for (String linea: codigo) {
            println(linea);
        }*/

        print("Cantidad de linas : %d", codigo.length );

        print("Media: (%d)", media.size());

        print("Parrafos: (%d)", parrafos.size());


        print("Forms: (%d)", formularios.size());
        for (Element form : formularios){
            print(" * %s <%s> (%s)", form.tagName() ,form.attr("method"), form.attr("action"));

            for (Element input : form.children()){

                if(input.tagName() == "input"){
                    print("  - %s <%s> (%s) (%s)", input.tagName() ,input.attr("type"), input.attr("id"), input.attr("name"));
                }
            }

            metotodo = form.attr("method");
            System.out.println(metotodo);
            System.out.println("\n");
            if(metotodo.equalsIgnoreCase("post") ){

                print("Enviando parametros...");

                Connection.Response loginForm = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .execute();

                Document document = Jsoup.connect( url_request +  form.attr("action"))
                        .data("asignatura", "practica1")
                        .cookies(loginForm.cookies())
                        .post();

                print("\n RESPUESTA DEL FORMULARIO: \n");
                System.out.println(document);
                // print(loginForm.body());
                //print(loginForm.url().toString());
            }
        }

        print("Imports: (%d)", imports.size());

        print("Links: (%d)", links.size());
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}


