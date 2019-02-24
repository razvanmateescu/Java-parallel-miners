import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class that implements the channel used by wizards and miners to communicate.
 */
public class CommunicationChannel {
	ArrayBlockingQueue<Message> mesaj;// = new ArrayBlockingQueue<Message>(1000);;
	ArrayBlockingQueue<Message> vrajitor; // = new ArrayBlockingQueue<Message>(1000);
	int nr=0;
	int nr2=0;
	//Message v= null;
	/**
	 * Creates a {@code CommunicationChannel} object.
	 */
	public CommunicationChannel() {
		mesaj = new ArrayBlockingQueue<Message>(1000);
		vrajitor = new ArrayBlockingQueue<Message>(1000);
		
	}

	/**
	 * Puts a message on the miner channel (i.e., where miners write to and wizards
	 * read from).
	 * 
	 * @param message
	 *            message to be put on the channel
	 * @throws InterruptedException 
	 */
	public void putMessageMinerChannel(Message message) {
		
		//mesaj.put(message);
		//System.out.println(message);
		
		//Lock l = new ReentrantLock();
		//l.lock();
		try {
			mesaj.put(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//finally {
			//	l.unlock();
			//}
	}

	/**
	 * Gets a message from the miner channel (i.e., where miners write to and
	 * wizards read from).
	 * 
	 * @return message from the miner channel
	 * @throws InterruptedException 
	 */
	public Message getMessageMinerChannel() {
		
		Message m = null;
		//Lock l = new ReentrantLock();
		//l.lock();
		//m=mesaj.take();
		try {
		m=mesaj.take();
		} catch(Exception e) {
			e.printStackTrace();
		}
		//finally {
	//		l.unlock();
	//	}
		return m;
	}

	/**
	 * Puts a message on the wizard channel (i.e., where wizards write to and miners
	 * read from).
	 * 
	 * @param message
	 *            message to be put on the channel
	 * @throws InterruptedException 
	 */
	public void putMessageWizardChannel(Message message) {
		
		//Lock l = new ReentrantLock();
		//l.lock();				
		try {
			
			vrajitor.put(message);
			nr++;
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		//finally { if (nr==2)
		//{
	    //     l.unlock();
	   //      nr=0;
		//}
	   //  }
		 
		 
	}

	/**
	 * Gets a message from the wizard channel (i.e., where wizards write to and
	 * miners read from).
	 * 
	 * @return message from the miner channel
	 * @throws InterruptedException 
	 */
	public Message getMessageWizardChannel() {
		Message v =null;
	//	Lock l = new ReentrantLock();
	//	l.lock();
		try {
			
		v=vrajitor.take();
		nr2++;
			
		//System.out.println(nr2);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	//	finally {
	//	if(nr2==2)
	//	{
	 //        l.unlock();
	 //        nr2=0;
	//	}
	//	}
		return v;
		//System.out.println(v.getData());
	//	try {
		//	System.out.println(vrajitor.take().getData());
		//} catch (InterruptedException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
	//	}
		
		
	}
}
