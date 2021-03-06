package org.emop.monitor.marks;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.emop.cache.Cache;
import org.emop.cache.impl.SimpleCache;
import org.emop.monitor.Benchmark;
import org.emop.monitor.StatusMark;




public class HTTPRequest extends StatusMark{
	public Cache cache = new SimpleCache();
	
	public void markRequest(Benchmark mark){
		super.markRequest(mark);
		String id = format(mark.obj + "");
		
		URLMark  urlMark = null;
		Object obj = cache.get(id, true);
		if(obj == null){
			urlMark = new URLMark();
			urlMark.uri = id;
			cache.set(id, urlMark, 60 * 60);
		}else {
			urlMark = (URLMark)obj;
		}		
		urlMark.markRequest(mark);
	}
	
	public List<String> urlList(){
		return cache.keys();
	}
	
	private String format(String url){
		URL u = null;
		try {
			u = new URL(url);
			return u.getAuthority() + u.getPath();
		} catch (MalformedURLException e) {
		}		
		return "127.0.0.1";
	}
}
