package ifpb.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifpb.Message;

public class MessageRepository {
	private List<Message> list = new ArrayList<>();
	
	public void add(Message msg) {
		System.out.println("Adicionando uma mensagem: " + msg.getId());
		list.add(msg);
	}
	
	public void remove(Message msg){
		System.out.println("Removendo uma mensagem: " + msg.getId());
		for (int i = 0; i < list.size(); i++) {
			Message message = list.get(i);
			if (msg.getId().equals(message.getId())) {
				list.remove(i); 
				break;
			}
		}
	}

	public List<Message> list() {
		return Collections.unmodifiableList(list);
	}
}
