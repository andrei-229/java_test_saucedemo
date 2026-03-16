package loadtesting

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

/**
 * Нагрузочный тест для Saucedemo
 * 
 * Сценарий:
 * 1. Логин (standard_user / password123)
 * 2. Просмотр каталога товаров
 * 3. Добавление товара в корзину
 * 4. Просмотр корзины
 * 5. Оформление покупки (Checkout)
 * 
 * Параметры можно задавать через переменные окружения:
 * - USERS: количество пользователей (default 10)
 * - RAMP_UP: время разогрева в секундах (default 30)
 * - DURATION: длительность теста в секундах (default 60)
 * - BASE_URL: базовый URL (default https://www.saucedemo.com)
 */
class SauceDemoSimulation extends Simulation {

  // Параметры из переменных окружения
  private val users = System.getenv().getOrDefault("USERS", "10").toInt
  private val rampUpSeconds = System.getenv().getOrDefault("RAMP_UP", "30").toInt
  private val durationSeconds = System.getenv().getOrDefault("DURATION", "60").toInt
  private val baseUrl = System.getenv().getOrDefault("BASE_URL", "https://www.saucedemo.com")

  // HTTP конфигурация
  private val httpProtocol = http
    .baseUrl(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
    .inferHtmlResources()

  // Данные для тестирования
  val credentials = csv("credentials.csv").random

  // Сценарий: типичный юзер-флоу
  val scn = scenario("Saucedemo Purchase Flow")
    .feed(credentials)
    .exec(session => {
      println(s"User: ${session("username").as[String]}")
      session
    })
    // 1. Загрузка главной страницы
    .exec(
      http("GET Home Page")
        .get("/")
        .check(status.in(200, 404))
    )
    .pause(1.seconds)

    // 2. Логин - отправка формы
    .exec(
      http("POST Login")
        .post("/")
        .formParam("user-name", "${username}")
        .formParam("password", "${password}")
        .check(status.in(200, 303, 404))
    )
    .pause(2.seconds)

    // 3. Просмотр страницы товаров
    .exec(
      http("GET Inventory Page")
        .get("/inventory.html")
        .check(status.in(200, 404))
    )
    .pause(1.5.seconds)

    // 4. Клик на первый товар и просмотр деталей
    .exec(
      http("GET Product Details")
        .get("/inventory-item.html?id=4")
        .check(status.in(200, 404))
    )
    .pause(1.seconds)

    // 5. Добавление товара в корзину
    .exec(
      http("Add to Cart")
        .get("/cart.html?action=add&product=4")
        .check(status.in(200, 404))
    )
    .pause(1.seconds)

    // 6. Просмотр другого товара
    .exec(
      http("GET Another Product")
        .get("/inventory-item.html?id=5")
        .check(status.in(200, 404))
    )
    .pause(1.seconds)

    // 7. Добавление еще одного товара в корзину
    .exec(
      http("Add Second Product to Cart")
        .get("/cart.html?action=add&product=5")
        .check(status.in(200, 404))
    )
    .pause(1.seconds)

    // 8. Переход на страницу корзины
    .exec(
      http("GET Cart Page")
        .get("/cart.html")
        .check(status.in(200, 404))
    )
    .pause(1.5.seconds)

    // 9. Начало оформления (Checkout)
    .exec(
      http("Click Checkout")
        .get("/checkout-step-one.html")
        .check(status.in(200, 404))
    )
    .pause(1.seconds)

    // 10. Заполнение данных покупателя
    .exec(
      http("Checkout Step One")
        .post("/checkout-step-one.html")
        .formParam("firstName", "Test")
        .formParam("lastName", "User")
        .formParam("postalCode", "12345")
        .check(status.in(200, 404))
    )
    .pause(1.seconds)

    // 11. Просмотр и подтверждение заказа
    .exec(
      http("GET Checkout Step Two")
        .get("/checkout-step-two.html")
        .check(status.in(200, 404))
    )
    .pause(1.seconds)

    // 12. Финализация заказа
    .exec(
      http("Finish Checkout")
        .post("/checkout-complete.html")
        .check(status.in(200, 404))
    )
    .pause(1.seconds)

    // 13. Логаут
    .exec(
      http("Logout")
        .get("/logout.html")
        .check(status.in(200, 404))
    )

  // Профили нагрузки
  setUp(
    scn.inject(
      rampUsers(users).during(rampUpSeconds.seconds)
    )
  )
    .maxDuration(durationSeconds.seconds)
    .protocols(httpProtocol)
    .assertions(
      global.responseTime.percentile(95).lt(5000),
      global.responseTime.percentile(99).lt(10000)
    )
}
