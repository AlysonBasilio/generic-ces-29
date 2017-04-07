import java.io.*;
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
	}
	
	private boolean checkProcesso(Processo p)
	{
		
	}
	
	private Processo persistProcesso(Processo p)
	{
		FileWriter out = new FileWriter("database/processos.txt", true); 
		BufferedWriter bw = new BufferedWriter(out);
		
		bw.write("-------------------------------------------------------------------------------------------------------");
		bw.write("Processo " + p.getId() + "\n");
		bw.write("Reclamante: " + p.getReclamante() + "\nTelefone: " + p.getTelefone() + "\nE-mail: " + p.getEmail() + "\n");
		bw.write("Conteúdo:\n" + p.getConteudo() + "\n");
	}
	
	private void sendInfoByEmail(Processo p, boolean statusProcesso)
	{
		
	}
}
