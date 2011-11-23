package com.yutax77.lrucache

import scala.collection.mutable.Map
import scala.collection.mutable.Queue
/**
 * 型パラメータ化されたLRUキャッシュ
 * sizeで指定する数だけの要素を保持する.
 * #putでsize以上の要素を追加した場合最も古い要素が削除される。
 */
class LRUCache[K, V] (size : Int){
    require(size > 0)//キャッシュのサイズは0より大きくなければいけない
	val map:Map[K,V] = Map()//キャッシュを管理するマップ
	var history:List[K] = List.empty[K]//キーを管理するリスト

    /**
     * キャッシュに要素を追加する
     */
	def put (key : K, value : V){
	  if (history.size >= size) {
	    map.remove(history.head)
	    history = history.tail
	  }
	  map.put(key, value)
	  history = history ::: List(key)//TODO 同じキーが追加された場合のチェックを行う
	}
    /**
     * キャッシュの要素を取得する.
     * @param key 取得する要素のキー
     * @return 
     */
	def get (key : K) : Option[V] = {
	  history = (history - key) ::: List(key)
	  map.get(key)
	}

}