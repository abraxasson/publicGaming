package hof.net;

import hof.level.objects.AbstractCloud;

import java.util.LinkedList;

public class SmsProcessing {
	private static SmsProcessing instance;
	private LinkedList<AbstractCloud> list;
	
	private SmsProcessing(){
		list = new LinkedList<AbstractCloud>();
	}
	
	public void addEffect(AbstractCloud effect){
		this.list.add(effect);
	}
	
	public LinkedList<AbstractCloud> getList(){
		return this.list;
	}
	
	public static SmsProcessing getInstance(){
		if(instance == null){
			instance = new SmsProcessing();
		}
		return instance;
	}

}
