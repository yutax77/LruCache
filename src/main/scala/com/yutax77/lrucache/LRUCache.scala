package com.yutax77.lrucache

import scala.collection.mutable.Map
import scala.collection.mutable.Queue

class LRUCache (size : Int){
	var value:String = null
	val map:Map[String,String] = Map()
	var history:List[String] = List.empty[String]
	
	def put (key : String, value : String){
	  if (history.size >= size) {
	    map.remove(history.head)
	    history = history.tail
	  }
	  map.put(key, value)
	  history = history ::: List(key)
	}
	def get (key : String) : String = {
	  history = history.drop(history.indexOf(key)) ::: List(key)
	  val value = map.get(key)
	   value match {
	    case None => null
	    case Some(s) => s
	  }
	}

}