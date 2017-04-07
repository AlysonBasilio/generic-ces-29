import java.io.*;

public class ControladorSIAPJ {
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
