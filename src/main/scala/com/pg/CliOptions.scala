package com.pg

class CliOptions(arglist: List[String]) extends Serializable {

  type OptionMap = Map[Symbol, Any]

  val DEFAULT_CLEAN_LOGS_DB = "data"
  val DEFAULT_CLEAN_LOGS_TABLE = "app_events"


  val optionMap = parseOptions(arglist)

  val dt = getValueString('dt)
  val h = getValueString('h)
  val cleanlogsdb = getValueString('cleanlogsdb)
  val cleanlogstable = getValueString('cleanlogstable)


  def getValueString(s: Symbol): String = {
    optionMap.getOrElse(s, "none").toString
  }

  def getValueInt(s: Symbol): Int = {
    def value = optionMap.get(s)
    if(value == None){
      return -1
    }
    value.get.toString.toInt
  }

  def getValueBoolean(s: Symbol): Boolean = optionMap.getOrElse(s, false).asInstanceOf[Boolean]

  def parseOptions(arglist: List[String]): OptionMap = {
    def nextOption(map: OptionMap, list: List[String]): OptionMap = {
      (list: @unchecked) match {
        case Nil => map
        case "--h" :: value :: tail =>
          nextOption(map ++ Map('h -> value), tail)
        case "--dt" :: value :: tail =>
          nextOption(map ++ Map('dt -> value), tail)
        case "--cleanlogsdb" :: value :: tail =>
          nextOption(map ++ Map('cleanlogsdb -> value), tail)
      }
    }
    var options = nextOption(Map(), arglist)

    if (!options.contains('cleanlogsdb)) options = options ++ Map('cleanlogsdb -> DEFAULT_CLEAN_LOGS_DB)
    if (!options.contains('cleanlogstable)) options = options ++ Map('cleanlogstable -> DEFAULT_CLEAN_LOGS_TABLE)

    options
  }

}
