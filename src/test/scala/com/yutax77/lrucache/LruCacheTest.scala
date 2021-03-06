package com.yutax77.lrucache
import org.scalatest.junit.JUnitSuite
import org.scalatest.junit.ShouldMatchersForJUnit
import org.junit.Test
import java.util.concurrent.Executors

class LRUCacheTest extends JUnitSuite with ShouldMatchersForJUnit {
	@Test
	def dummy() {
	  true should be (true)
	}
	@Test
	def testPut() {
	  val cache = new LRUCache[String, String](5)
	  cache.put("key", "value")
	}
	@Test
	def testGet無いキーはNULL() {
	  val cache = new LRUCache[String, String](5)
	  cache.put("A", "B")
	  cache.get("") should be (None)
	}
	
	@Test
	def testGetNormal () {
	  val chache = new LRUCache[String, String](5)  
	  chache.put("key", "value")
	  chache. get("key") should be (Some("value"))
	  chache.put("b", "valB");
	  chache. get("key") should be (Some("value"))
	}
	
	@Test
	def testLimitより多い要素をPUTすると一番古いものから消える() = {
	  val cache = new LRUCache[String, String](3)
	  cache.put("A", "A")
	  cache.put("B", "B")
	  cache.put("C", "C")
  	  cache.put("D", "D")
	  cache.get("A") should be(None)
	  cache.get("B") should be(Some("B"))
	  cache.get("C") should be(Some("C"))
	  cache.get("D") should be(Some("D"))
	}
	
	@Test
	def get時に最近アクセスしてないものから消される() = {
	  val cache = new LRUCache[String, String](3)
	  cache.put("A", "A")
	  cache.put("B", "B")
	  cache.put("C", "C")
	  cache.get("A")
  	  cache.put("D", "D")
	  cache.get("A") should be(Some("A"))
	  cache.get("B") should be(None)
	}
	@Test(expected=classOf[IllegalArgumentException])
	def sizeMustBeOverZero():Unit = {
	  new LRUCache[String, String](0)
	}
	@Test
	def 重複するキー対策() = {
	  val cache = new LRUCache[String, String](3)
	  cache.put("A", "A1")
	  cache.put("A", "A2")
	  cache.put("A", "A3")
	  cache.put("B","B")
	  cache.put("C", "C")
	}
	@Test
	def リサイズ() = {
	  val cache = new LRUCache[String, String](3)
	  cache.put("A", "A")
	  cache.put("B", "B")
	  cache.put("C", "C")
	  cache.put("D", "D")
	  cache.get("A") should be (None)
	  cache.resize(4)
	  cache.put("E", "E")
	  cache.get("B") should be (Some("B"))
	}
	@Test
	def 小さくリサイズ() = {
	  val cache = new LRUCache[String, String](3)
	  cache.put("A", "A")
	  cache.put("B", "B")
	  cache.put("C", "C")
	  cache.resize(1)
	  cache.get("B") should be (None)
	  cache.get("A") should be (None)
	  cache.get("C") should be (Some("C"))
	}
	
	@Test
	def 一定時間経つと取得できない() = {
	  val cache = new LRUCache[String, String](3,2000)
	  cache.put("A", "a")
	  Thread.sleep(2000);
	  //2秒経過したので取得できない
	  cache.get("A") should be (None)
	}
	
	@Test
	def 時間経過する前にgetするとアクセス時間が更新される() = {
	  val cache = new LRUCache[String, String](3,2000)
	  cache.put("A", "A")
	  Thread.sleep(1000)
	  cache.get("A")
	  Thread.sleep(1000)
	  cache.get("A") should be (Some("A"))
	  Thread.sleep(2000)
	  cache.get("A") should be (None)	  
	}
	
    @Test
    def 複数スレッドでputしても全部正しく入る() {
		val cache = new LRUCache[Int, Int](100, 60000)
		val getRunFunc = (start: Int) =>  {
			new Runnable {
				def run() {
					for(i <- start to start + 50)
						cache.put(i, i)
				}
			}
		}
		val execService = Executors.newFixedThreadPool(2)
		execService.execute(getRunFunc(0))
		execService.execute(getRunFunc(51))
		//TODO 2つのスレッドの終了を待ってチェックを開始する
    }
	
}