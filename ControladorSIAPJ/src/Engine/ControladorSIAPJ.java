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
	private Session session;
	private Map<Integer, Processo> processos = new HashMap<Integer, Processo>();
	String meuEmail = "xupadeputado@gmail.com";
	
	public void init () {
		 props = new Properties();
         /** Parâmetros de conexão com servidor Gmail */
         props.put("mail.smtp.host", "smtp.gmail.com");
         props.put("mail.smtp.socketFactory.port", "465");
         props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.port", "465");
         

 		session = Session.getDefaultInstance(props,
                 new javax.mail.Authenticator() {
                      protected PasswordAuthentication getPasswordAuthentication() 
                      {
                            return new PasswordAuthentication(meuEmail, "xupadeputado1");
                      }
                 });
 		
 		session.setDebug(true);
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
	}
	
	private void sendInfoByEmail(Processo p, boolean statusProcesso)
	{
		
	}

	@Override
	public boolean validateProcess(Processo proc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendEmail(String address) {
		try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(meuEmail)); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                       .parse(address);  
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Enviando email com JavaMail");//Assunto
            message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
            /**Método para enviar a mensagem criada*/
            Transport.send(message);
            System.out.println("Feito!!!");
       } catch (MessagingException e) {
            return false;
      }
		
		return true;
	}
	
	public boolean addProcesso(Processo proc) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Processo getProcesso(int id) {
		return this.processos.get(id);
	}
}
