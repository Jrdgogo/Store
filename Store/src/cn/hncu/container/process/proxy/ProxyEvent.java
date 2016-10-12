package cn.hncu.container.process.proxy;



public class ProxyEvent {

	private Proxy proxy;
	public ProxyEvent(Proxy proxy) {
		this.proxy=proxy;
	}
	public Proxy getSource(){
		return proxy;
	}
	
	public String getProxyCommand(){
		return proxy.getProxyCommand();
	}
}
