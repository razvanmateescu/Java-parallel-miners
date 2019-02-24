import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class for a miner.
 */
public class Miner extends Thread {
	Integer hashCount;
	Set<Integer> solved;
	CommunicationChannel channel;
	Object o1 = new Object();
	Object o2 = new Object();
	/**
	 * Creates a {@code Miner} object.
	 * 
	 * @param hashCount
	 *            number of times that a miner repeats the hash operation when
	 *            solving a puzzle.
	 * @param solved
	 *            set containing the IDs of the solved rooms
	 * @param channel
	 *            communication channel between the miners and the wizards
	 *            
	 */
	
	private static String encryptMultipleTimes(String input, Integer count) {
        String hashed = input;
        for (int i = 0; i < count; ++i) {
            hashed = encryptThisString(hashed);
        }

        return hashed;
    }
	
	 private static String encryptThisString(String input) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
	            
	            // convert to string
	            StringBuffer hexString = new StringBuffer();
	            for (int i = 0; i < messageDigest.length; i++) {
	            String hex = Integer.toHexString(0xff & messageDigest[i]);
	            if(hex.length() == 1) hexString.append('0');
	                hexString.append(hex);
	            }
	            return hexString.toString();
	    
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }
	public Miner(Integer hashCount, Set<Integer> solved, CommunicationChannel channel) {
		this.hashCount=hashCount;
		this.solved=solved;
		this.channel=channel;
		
	}

	@Override
	public void run() {
		
		
		while(true)
		{
			
			Message mes=null;
			Message mes2=null;
			
		Lock l = new ReentrantLock();
		
		l.lock();	
		try {
			
			
			mes = channel.getMessageWizardChannel();
			while (mes.getData().equals("END"))
			{	
				mes = channel.getMessageWizardChannel();
			}
			//if (mes.getData() == "END")
			//{	mes = channel.getMessageWizardChannel();
				
			//}
			
			mes2 = channel.getMessageWizardChannel();
		}
		finally {
	        l.unlock();
		}
		
			if (!solved.contains(mes2.getCurrentRoom()))
			{
				System.out.println(mes.getCurrentRoom());
				//System.out.println(mes.getParentRoom());
				String date2 = mes2.getData();
				String decrypt = encryptMultipleTimes(date2, hashCount);
				Message mineri=new Message(mes.getCurrentRoom(),mes2.getCurrentRoom(),decrypt);
				synchronized(o1) {
				solved.add(mes2.getCurrentRoom());
				}
				channel.putMessageMinerChannel(mineri);	
				
		
		}
			
			
			//if (mes2.getData().equals("EXIT"))
			//{	
			//	return;
			//}
			
		}
		
	}
}
