package hof.net.userMessages;

import hof.level.objects.AbstractCloud;

public class SMSInfoMessage extends AbstractMessage {

	private static final long serialVersionUID = 37295872L;
	private AbstractCloud effect;
	
	public SMSInfoMessage(AbstractCloud effect) {
		super(Type.SMSInfo);
		this.effect = effect;
	}

	public AbstractCloud getEffect() {
		return effect;
	}

}
