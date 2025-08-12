import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private List<Feedback> feedbacks = new ArrayList<>();
    private Gson gson = new Gson();
    private File jsonFile = new File("feedbacks.json");
    private File htmlFile = new File("C://projetovs//app.js//src/index.html");
    private TextArea feedbackListArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        carregarFeedbacks();

        Label nameLabel = new Label("Nome do Cliente:");
        TextField nameField = new TextField();

        Label messageLabel = new Label("Feedback:");
        TextArea messageArea = new TextArea();

        Button submitButton = new Button("Enviar Feedback");
        submitButton.setOnAction(e -> {
            String nome = nameField.getText().trim();
            String mensagem = messageArea.getText().trim();

            if (!nome.isEmpty() && !mensagem.isEmpty()) {
                Feedback fb = new Feedback(nome, mensagem);
                feedbacks.add(fb);
                salvarFeedbacks();
                gerarHTML();
                atualizarLista();
                nameField.clear();
                messageArea.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Preencha todos os campos!", ButtonType.OK);
                alert.show();
            }
        });

        feedbackListArea = new TextArea();
        feedbackListArea.setEditable(false);
        atualizarLista();

        VBox root = new VBox(10, nameLabel, nameField, messageLabel, messageArea, submitButton, new Label("Feedbacks Recebidos:"), feedbackListArea);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Sistema de Feedback - Customer Success");
        stage.setScene(scene);
        stage.show();
    }

    private void salvarFeedbacks() {
        try (Writer writer = new FileWriter(jsonFile)) {
            gson.toJson(feedbacks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gerarHTML() {
        StringBuilder html = new StringBuilder();
        html.append("""
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Feedbacks dos Clientes</title>
                <style>
                    body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
                    h1 { text-align: center; color: #333; }
                    .feedback { background: white; padding: 15px; margin: 10px 0; border-radius: 8px;
                                box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
                    .nome { font-weight: bold; color: #0077cc; }
                    .mensagem { margin-top: 5px; color: #555; }
                </style>
            </head>
            <body>
                <h1>Feedbacks Recebidos</h1>
        """);

        for (Feedback fb : feedbacks) {
            html.append("<div class='feedback'>")
                .append("<div class='nome'>").append(fb.nome).append("</div>")
                .append("<div class='mensagem'>").append(fb.mensagem).append("</div>")
                .append("</div>");
        }

        html.append("</body></html>");

        try (Writer writer = new FileWriter(htmlFile)) {
            writer.write(html.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarFeedbacks() {
        if (jsonFile.exists()) {
            try (Reader reader = new FileReader(jsonFile)) {
                Type listType = new TypeToken<ArrayList<Feedback>>() {}.getType();
                feedbacks = gson.fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void atualizarLista() {
        StringBuilder sb = new StringBuilder();
        for (Feedback fb : feedbacks) {
            sb.append(fb.nome).append(": ").append(fb.mensagem).append("\n");
        }
        feedbackListArea.setText(sb.toString());
    }

    class Feedback {
        String nome;
        String mensagem;

        Feedback(String nome, String mensagem) {
            this.nome = nome;
            this.mensagem = mensagem;
        }
    }
}
