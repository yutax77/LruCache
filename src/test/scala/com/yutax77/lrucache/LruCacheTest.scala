package com.yutax77.lrucache
import org.scalatest.junit.JUnitSuite
import org.scalatest.junit.ShouldMatchersForJUnit
import org.junit.Test

class LruCacheTest extends JUnitSuite with ShouldMatchersForJUnit {
	@Test
	def dummy() {
	  true should be (true)
	}
	@Test
	def testPut() {
	  val cache:LRUCache = new LRUCache(5)
	  cache.put("key", "value")
	}
	@Test
	def testGet無いキーはNULL() {
	  val cache:LRUCache = new LRUCache(5)
	  cache.put("A", "B")
	  cache.get("") should be (null)
	  
	  	  
	}
	
	@Test
	def testGetNormal () {
	  val chache:LRUCache = new LRUCache(5)  
	  chache.put("key", "value")
	  chache. get("key") should be ("value")
	  chache.put("b", "valB");
	  chache. get("key") should be ("value")
	}
	
	@Test
	def testLimitより多い要素をPUTすると一番古いものから消える() = {
	  val cache = new LRUCache(3)
	  cache.put("A", "A")
	  cache.put("B", "B")
	  cache.put("C", "C")
  	  cache.put("D", "D")
	  cache.get("A") should be(null)
	  cache.get("B") should be("B")
	  cache.get("C") should be("C")
	  cache.get("D") should be("D")
	}
	
	@Test
	def get時に最近アクセスしてないものから消される() = {
	  val cache = new LRUCache(3)
	  cache.put("A", "A")
	  cache.put("B", "B")
	  cache.put("C", "C")
	  cache.get("A")
  	  cache.put("D", "D")
	  cache.get("A") should be(null)
	  cache.get("B") should be(null)
	}
	
	
}