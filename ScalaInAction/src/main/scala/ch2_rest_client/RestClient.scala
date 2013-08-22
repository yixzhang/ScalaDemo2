package ch2_rest_client

import org.apache.http.message.{BasicNameValuePair, BasicHeader}
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{HttpDelete, HttpGet, HttpPost}
import org.apache.http.impl.client.{DefaultHttpClient, BasicResponseHandler}

object RestClient{
	
	def main(args: Array[String]){
		
		require(args.size >= 2, "At minmum, you should specify action(get, post, delete) and url")
		val command = args.head
		val params = parseArgs(args)
		val url = args.last
		
		command.toLowerCase match {
			case "get"    => handleGetRequest(url, params)
			case "post"   => handlePostRequest(url, params)
			case "delete" => handleDeleteRequest(url, params)
		}
	}
	
	//Key: "-p" or "-h"  Value: list of parameters
	def parseArgs(args: Array[String]) : Map[String, List[String]] = {
		
		def genEntity(key: String) = {
			def values(commaSepValues: String) = commaSepValues.split(",").toList
			
			val index = args.indexOf(key)
			(key, if(index == -1) Nil else values(args(index+1)))
		} 
		
		Map(genEntity("-p"), genEntity("-h"))
	} 
	
	def splitByEqual(nameValue:String): Array[String] = nameValue.split('=')
	
	def headers(params: Map[String, List[String]]) = for(nameValue <- params("-h")) yield {
		def tokens = splitByEqual(nameValue)
		new BasicHeader(tokens(0), tokens(1))
	}

  def formEntity(params:Map[String, List[String]]) = {
    def toJavaList(scalaList: List[BasicNameValuePair]) = java.util.Arrays.asList(scalaList.toArray:_*)

    def formParams = for(nameValue <- params("-p")) yield {
      def tokens = splitByEqual(nameValue)
      new BasicNameValuePair(tokens(0), tokens(1))
    }

    def formEntity = new UrlEncodedFormEntity(toJavaList(formParams), "UTF-8")
    formEntity
  }
	
	def handlePostRequest(url: String, params: Map[String, List[String]]){
		val httpport = new HttpPost(url)
    headers(params).foreach { httpport.addHeader(_) }
    httpport.setEntity(formEntity(params))
    val responseBody = new DefaultHttpClient().execute(httpport, new BasicResponseHandler())
    println(responseBody)
	}
	
	def handleGetRequest(url: String, params: Map[String, List[String]]){
		val query = params("-p").mkString("&")
		val httpget = new HttpGet(s"${url}?${query}")
		headers(params).foreach { httpget.addHeader(_) }
		val responseBody = new DefaultHttpClient().execute(httpget, new BasicResponseHandler())
		println(responseBody)
	}
	
	def handleDeleteRequest(url: String, params: Map[String, List[String]]){
		val httpDelete = new HttpDelete(url)
		val httpResponse = new DefaultHttpClient().execute(httpDelete)
		println(httpResponse.getStatusLine)
	}
}