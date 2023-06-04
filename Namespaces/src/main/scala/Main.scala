import ru.Module.{name, name2}
//ru.Module._  - импорт всего
//import ru.Module.{name => olegName} - переименновывание объекта
//import ru.Module.{_, name2 => _} - импорт всего, кроме name2
//что-то типо подключения хедеров в с++
//позволяет работать с переменными, хранящимися в пакетах
//так же можно подключать не только пакет, но и переменные хранящиеся в нем.

object Main extends App {

  object Config {
    val name = "Hello, "
  }

  def greeting(name: String){
    import Config.{name => prefix}
    println(prefix + name)
  }

  val name = "Oleg"
  greeting(name)
}