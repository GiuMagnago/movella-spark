package com.movella.app;

import static spark.Spark.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.movella.responses.Unauthorized;
import com.movella.service.ContatoService;
import com.movella.service.UsuarioService;

import spark.ModelAndView;
import spark.Session;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
  public static void main(String[] args) {
    try {
      final int port = getHerokuAssignedPort();

      port(port);

      staticFiles.location("/public");

      before((req, res) -> {
        System.out.println(String.format("[%s] - %s %s %s",
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new Date(System.currentTimeMillis())), req.ip(),
            req.requestMethod(), req.url()));
        System.out.println(req.body());
      });

      notFound((req, res) -> {
        return render(new HashMap<>(), "/not-found");
      });

      internalServerError((req, res) -> {
        return render(new HashMap<>(), "/error");
      });

      get("/", (req, res) -> {
        return render(new HashMap<>(), "/moveis");
      });

      get("/moveis", (req, res) -> {
        return render(new HashMap<>(), "/moveis");
      });

      get("/sobre", (req, res) -> {
        return render(new HashMap<>(), "/sobre");
      });

      get("/contato", (req, res) -> {
        return render(new HashMap<>(), "/contato");
      });

      get("/perfil", (req, res) -> {
        return render(new HashMap<>(), "/perfil");
      });

      get("/entrar", (req, res) -> {
        return render(new HashMap<>(), "/entrar");
      });

      get("/criarconta", (req, res) -> {
        return render(new HashMap<>(), "/criarconta");
      });

      before("/api/*", (req, res) -> {
        final String splat = req.splat()[0];

        if (splat.equals("login") || splat.equals("register") || splat.equals("contato/create"))
          return;

        if (req.session().attribute("user") == null)
          new Unauthorized(res);
      });

      path("/api", () -> {
        post("/login", UsuarioService.login);

        post("/register", UsuarioService.register);

        get("/contato/:id", ContatoService.read);

        post("/contato/create", ContatoService.create);
      });

      System.out.println(String.format("listening on port %d", port));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  static int getHerokuAssignedPort() {
    final String port = new ProcessBuilder().environment().get("PORT");
    return port == null ? 80 : Integer.parseInt(port);
  }

  public static String render(Map<String, Object> model, String templatePath) {
    return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
  }
}

// TODO: Seu código back-end (na pasta src/main/java) tem os pacotes app, dao,
// model e service?
// TODO: Sua pasta de recursos está organizada em uma pasta para cada um dos
// recursos? Por exemplo, as pastas front-end, script-bd e imagens.
// TODO: Suas classes java seguem princípios de desenvolvimento Java?
// Comentários padronizados, atributos privados começando com letra minúscula e
// tendo um método get/set?
// TODO: O código src/main/java/app/Aplicacao.java tem um
// insert/update/remove/get/listar para cada um dos CRUDs?
// TODO: O pacote dao tem uma classe dao para cada um dos CRUDs? Cada classe DAO
// tem, pelo menos, um método para cada uma das operações
// insert/update/remove/get/listar? Temos também um método, por exemplo, para
// abrir a conexão com o banco de dados e outro para fechá-la?
// TODO: O pacote service tem uma classe para cada um dos CRUDs? Cada uma dessas
// classes tem, pelo menos, um método para cada uma das operações de
// insert/update/remove/get/listar?
// TODO: O front-end invoca cada um dos CRUDs? Para cada CRUD, o front-end tem
// as opções de insert/update/remove/get/listar?