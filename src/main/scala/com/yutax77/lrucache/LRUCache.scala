package com.yutax77.lrucache

import scala.collection.mutable.Map
import scala.collection.mutable.Queue

class LRUCache[K, V] (size : Int){
	var value:String = null
	val map:Map[K,V] = Map()
	var history:List[K] = List.empty[K]
	
	def put (key : K, value : V){
	  if (history.size >= size) {
	    map.remove(history.head)
	    history = history.tail
	  }
	  map.put(key, value)
	  history = history ::: List(key)
	}
	def get (key : K) : Option[V] = {
	  history = (history - key) ::: List(key)
	  map.get(key)
	}

}