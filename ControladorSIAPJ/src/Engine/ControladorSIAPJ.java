package Engine;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import Interfaces.*;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
	
public class ControladorSIAPJ implements ServicoEmail, RepositorioProcessos, ValidadorProcesso {

	private Properties props;
	private Map<Integer, Processo> processos = new HashMap<Integer, Processo>();
	
	public void init () {
		 props = new Properties();
         /** Parâmetros de conexão com servidor Gmail */
         props.put("mail.smtp.host", "smtp.gmail.com");
         props.put("mail.smtp.socketFactory.port", "465");
         props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.port", "465");
	}
	
	public boolean initProcesso(Processo p)
	{
		boolean ok = checkProcesso(p);
		if (ok)
		{
			persistProcesso(p);
			sendInfoByEmail(p, ok);
		}
		else
			sendInfoByEmail(p, ok);
		return ok;
	}
	
	private boolean checkProcesso(Processo p)
	{
		return validateProcess(p);
	}
	
	private Processo persistProcesso(Processo p)
	{
		try {
			FileWriter out = new FileWriter("database/processos.txt", true); 
			BufferedWriter bw = new BufferedWriter(out);
		
			bw.write("-------------------------------------------------------------------------------------------------------");
			bw.write("Processo " + p.getID() + "\n");
			bw.write("Reclamante: " + p.getNomeReclamante() + "\nTelefone: " + p.getTelefone() + "\nE-mail: " + p.getEmail() + "\n");
			bw.write("Conteúdo:\n" + p.getContent() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	private void sendInfoByEmail(Processo p, boolean statusProcesso)
	{
		
	}

	
	public boolean validateProcess(Processo proc) {
		return proc.getID() > 0;
	}

	
	public boolean sendEmail(String address) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean addProcesso(Processo proc) {
		processos.put(proc.getID(), proc);
		return false;
	}

	
	public Processo getProcesso(int id) {
		return this.processos.get(id);
	}
	
	
}
