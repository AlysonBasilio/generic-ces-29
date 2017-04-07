package Engine;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import Interfaces.*;

public class ControladorSIAPJ implements RepositorioProcessos, ServicoEmail, ValidadorProcesso{
	
	private Map<Integer, Processo> processos = new HashMap<Integer, Processo>();
	
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

	
	public boolean validateProcess(Processo proc) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean sendEmail(String address) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean addProcesso(Processo proc) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public Processo getProcesso(int id) {
		return this.processos.get(id);
	}
	
	
}
