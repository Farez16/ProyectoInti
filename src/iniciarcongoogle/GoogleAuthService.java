package iniciarcongoogle;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;

import java.awt.Desktop;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;
import java.util.Scanner;

public class GoogleAuthService {
    public static String[] autenticarYObtenerDatos() throws Exception {
        String nombreArchivo = "client_secret_1043810316149-b8gv2ja9o8edqv3pkrh5m1qsluv39744.apps.googleusercontent.com.json";
        InputStream in = GoogleAuthService.class.getClassLoader().getResourceAsStream(nombreArchivo);
        if (in == null) {
            throw new Exception("No se encontró el archivo de credenciales: " + nombreArchivo);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
            JacksonFactory.getDefaultInstance(),
            new InputStreamReader(in)
        );

        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            transport,
            jsonFactory,
            clientSecrets,
            Arrays.asList("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email")
        ).setAccessType("offline").build();

        String url = flow.newAuthorizationUrl().setRedirectUri("urn:ietf:wg:oauth:2.0:oob").build();
        Desktop.getDesktop().browse(new URI(url));
        System.out.println("Pega el código de autorización aquí:");
        String code = new Scanner(System.in).nextLine();

        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri("urn:ietf:wg:oauth:2.0:oob").execute();

        GoogleCredential credential = new GoogleCredential.Builder()
            .setTransport(transport)
            .setJsonFactory(jsonFactory)
            .setClientSecrets(clientSecrets)
            .build()
            .setFromTokenResponse(response);

        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        GenericUrl urlInfo = new GenericUrl("https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
        HttpRequest request = requestFactory.buildGetRequest(urlInfo);
        String jsonIdentity = request.execute().parseAsString();

        return new String[]{jsonIdentity};
    }
}