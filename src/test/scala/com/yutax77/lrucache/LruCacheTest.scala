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
	
	
}