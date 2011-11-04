package com.yutax77.lrucache

import scala.collection.mutable.Map

class LRUCache (size : Int){
	var value:String = null
	val map:Map[String,String] = Map()
	
	def put (key : String, value : String){
		map.put(key, value)
	}
	def get (key : String) : String = {
	  val value = map.get(key)
	   value match {
	    case None => null
	    case Some(s) => s
	  }
	}

}