package com.yutax77.lrucache

import scala.collection.mutable.Map
import scala.collection.mutable.Queue
import java.util.Calendar
/**
 * 型パラメータ化されたLRUキャッシュ
 * sizeで指定する数だけの要素を保持する.
 * #putでsize以上の要素を追加した場合最も古い要素が削除される。
 */
class LRUCache[K, V] (siz : Int, tim : Int){

	def this(siz : Int) = this(siz, 2000)
	
    require(siz > 0)//キャッシュのサイズは0より大きくなければいけない
    var size = siz
	val map:Map[K,V] = Map()//キャッシュを管理するマップ
	var history:List[K] = List.empty[K]//キーを管理するリスト
	
    val timerMap:Map[K, Calendar] = Map()
    /**
     * キャッシュに要素を追加する
     */
	def put (key : K, value : V){
	  history -= key      
      if (history.size >= size) {
	    map.remove(history.head)
	    history = history.tail
	  }
	  map.put(key, value)
	  
	  //要素が追加された時間を記録しておく
	  timerMap.put(key, Calendar.getInstance())
	  
	  history = history ::: List(key)//TODO 同じキーが追加された場合のチェックを行う
	}
    /**
     * キャッシュの要素を取得する.
     * @param key 取得する要素のキー
     * @return 
     */
	def get (key : K) : Option[V] = {
	//キーの生存時間をチェックして、無効ならば削除する
	  val time = timerMap.get(key)
	  time match {
	    case Some(t) if(!isValid(t)) => {
	      timerMap.remove(key)
	      map.remove(key)
	      history -= key
	    }
	    case Some(t) => {
	      timerMap.put(key, Calendar.getInstance())
	    }
	    case None =>{}
	  }
	  val retVal = map.get(key)
	  if (retVal.isInstanceOf[Some[V]]) {
		  history = (history - key) ::: List(key)
	  }
	  return retVal
	}
	
	def isValid(t:Calendar): Boolean = {
	  val time = Calendar.getInstance()
	  time.add(Calendar.MILLISECOND, - tim)//生存期間を計算
	  time.before(t)
	}
	
	def resize (newSize : Int) = {
	  require(newSize > 0)
	  size = newSize
      while (history.size > size) {
	    map.remove(history.head)
	    history = history.tail
	  }
	}

}