package Engine;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import Interfaces.*;
import Mocks.MockBancoDeDados;

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
	
	public boolean initProcesso(Processo p, BancoDeDados db)
	{
		boolean ok = checkProcesso(p);
		if (ok)
			persistProcesso(p, db);
		sendInfoByEmail(p, ok);
		return ok;
	}
	
	private boolean checkProcesso(Processo p)
	{
		return validateProcess(p);
	}
	
	private Processo persistProcesso(Processo p, BancoDeDados db)
	{
		try {
			FileWriter out = new FileWriter("database/processos.txt", true); 
			BufferedWriter bw = new BufferedWriter(out);
		
			bw.write("-------------------------------------------------------------------------------------------------------");
			bw.write("Processo " + p.getID() + "\n");
			bw.write("Reclamante: " + p.getNomeReclamante() + "\nTelefone: " + p.getTelefone() + "\nE-mail: " + p.getEmail() + "\n");
			bw.write("Conteúdo:\n" + p.getContent() + "\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		db.persistirProcesso(p);
		
		return p;
	}
	
	private void sendInfoByEmail(Processo p, boolean statusProcesso)
	{
		init ();
		sendEmail (p.getEmail(), p, statusProcesso);
	}

	@Override
	public boolean validateProcess(Processo proc) {
		return proc.getID() > 0;
	}

	@Override
	public boolean sendEmail(String address, Processo proc, boolean status) {
		try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(meuEmail)); //Remetente

            String sucesso = "sucesso";
            if (!status) sucesso = "falha";
            
            Address[] toUser = InternetAddress //Destinatário(s)
                       .parse(address);
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Retorno do processo");//Assunto
            message.setText("Seu processo teve status de: " + sucesso + ".");
            /**Método para enviar a mensagem criada*/
            Transport.send(message);
            System.out.println("Feito!!!");
       } catch (MessagingException e) {
            return false;
      }
		
		return true;
	}
	
	public boolean addProcesso(Processo proc) {
		processos.put(proc.getID(), proc);
		return false;
	}
	
	public Processo getProcesso(int id) {
		return this.processos.get(id);
	}
	
	public static void main(String[] args) {
		Processo p = new Processo();
		MockBancoDeDados mock = new MockBancoDeDados();
		
		p.setContent("fhceiwuch");
		p.setEmail("generic@gmail.com");
		p.setID(36);
		p.setNomeReclamante("Zina");
		p.setTelefone("98399839");
		
		ControladorSIAPJ cont = new ControladorSIAPJ();
		
		cont.init();
		cont.initProcesso(p, mock);
		
		p.setID(-56);
		cont.initProcesso(p, mock);
	}
}
