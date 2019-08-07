package ru.xe72.notes.endpoints;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для статиического контента (index.html)
 */
@Controller
// @ResponseBody // Здесь не нужен!! Т.к. метод возвращает не сам ответ, а название страницы
public class StaticEndpoint {

  // Непонятная хрень, но работает. Все эти параметры нужны чтобы спринг не ругался на циклические
  // ссылки.
  // первый путь - аналог /*  Второй - /** (исключая /api/**)
  // в возвращаемом нужен именно forward. Чтобы был просто index.html, видимо нужно настраивать
  // resource handlers
  @GetMapping(value = {"/{x:\\w+}", "/{x:^(?!api$).*$}/**/{y:\\w+}"})
  public String forward404() {
    return "forward:/index.html";
  }
}
