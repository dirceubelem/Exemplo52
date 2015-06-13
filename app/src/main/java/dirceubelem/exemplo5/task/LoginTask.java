package dirceubelem.exemplo5.task;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dirceubelem.exemplo5.fw.Util;
import dirceubelem.exemplo5.to.TOUsuario;

/**
 * Created by dirceubelem on 13/06/15.
 */
public class LoginTask extends AsyncTask<String, Void, TOUsuario> {

    @Override
    protected TOUsuario doInBackground(String... params) {

        String usuario = params[0];
        String senha = params[1];

        TOUsuario t = null;

        try {

            String dados = "usuario=" + usuario + "&senha=" + senha;

            URL url = new URL("http://apiexemplo.fourtime.com/services/usuario/autenticar");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            http.setRequestProperty("Content-Length", Integer.toString(dados.length()));

            http.setUseCaches(false);

            http.setReadTimeout(15000);
            http.setConnectTimeout(20000);

            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream os = http.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(dados);
            bw.flush();
            bw.close();

            InputStream i = new BufferedInputStream(http.getInputStream());
            String result = Util.readString(i);

            t = TOUsuario.createByJson(result, TOUsuario.class);

        } catch (Exception e) {
            t = null;
        }

        return t;
    }
}
