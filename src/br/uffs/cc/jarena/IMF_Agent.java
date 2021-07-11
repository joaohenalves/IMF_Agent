package br.uffs.cc.jarena;

public class IMF_Agent extends Agente
{
	public int controleDeTurno;
	public int controleEnergia;
	public int controleBuscaCogumelo;
	public int controleCombate;
	public int turnosQueRecebeuEnergia;
	public int turnosQueBuscouCogumelo;
	public int turnosEmCombate;
	public String stringRaw = "";
	public String vetorIds[];
	public boolean recebendoEnergia = false;
	public boolean spreadInicial = false;
	public boolean buscandoCogumelo = false;
	public boolean ordemParaFicarParado = false;
	public boolean modoLutador = false;
	public boolean emCombate = false;


	public IMF_Agent(Integer x, Integer y, Integer energia) {

		super(x, y, energia);
		setDirecao(geraDirecaoAleatoria());

	}
	
	public void pensa() {

		controleDeTurno += 1; // Variável para o controle de várias ações.

		if (controleDeTurno == 1) { // Agentes enviam mensagens uns para os outros contendo seus IDs para serem armazenados em um vetor.
			enviaMensagem("meuid " + getId());
		}

		if (controleDeTurno == 2) { // Quebra a string contendo os IDs dos agentes.
			vetorIds = stringRaw.split(" ");
		}

		if (controleDeTurno == 10) { // Elege três agentes para se tornarem "lutadores".
			enviaMensagem("virem lutadores os ids " + vetorIds[1] + " " + vetorIds[2] + " " + vetorIds[3]);
		}

		if (controleDeTurno == 25) { // Elege um único agente para ficar parado até o fim do jogo.
			enviaMensagem("pare " + vetorIds[0]);
		}

		// Movimentação inicial para os agentes se espalharem caso o local de spawn foi um dos quatro cantos da arena, ou caso tenha sido em qualquer outro lugar.

		if (controleDeTurno <= 40 && controleDeTurno % 5 == 0 && !recebendoEnergia && !buscandoCogumelo && !ordemParaFicarParado) {

			if (getX() < 143 && getY() < 143) {
				double quad1 = Math.random();
				if (quad1 >= 0.50) { setDirecao(BAIXO); }
				else { setDirecao(DIREITA); }
			}

			else if (getX() > 717 && getY() < 143) {
				double quad2 = Math.random();
				if (quad2 >= 0.50) { setDirecao(BAIXO); }
				else { setDirecao(ESQUERDA); }
			}

			else if (getX() < 143 && getY() > 517) {
				double quad3 = Math.random();
				if (quad3 >= 0.50) { setDirecao(CIMA); }
				else { setDirecao(DIREITA); }
			}

			else if (getX() > 717 && getY() > 517) {
				double quad4 = Math.random();
				if (quad4 >= 0.50) { setDirecao(CIMA); }
				else { setDirecao(ESQUERDA); }
			}

			else {
				String stringId = String.valueOf(getId());

				if (stringId.equals(vetorIds[1]) || stringId.equals(vetorIds[4]) || stringId.equals(vetorIds[5])) {
					double rand1 = Math.random();
					if (rand1 <= 0.34 && getDirecao() != DIREITA) { setDirecao(ESQUERDA); }
					else if (rand1 > 0.34 && rand1 <= 0.68 && getDirecao() != ESQUERDA) { setDirecao(DIREITA); }
					else { setDirecao(CIMA); }
				}

				if (stringId.equals(vetorIds[2]) || stringId.equals(vetorIds[6]) || stringId.equals(vetorIds[7])) {
					double rand2 = Math.random();
					if (rand2 <= 0.34 && getDirecao() != DIREITA) { setDirecao(ESQUERDA); }
					else if (rand2 > 0.34 && rand2 <= 0.68 && getDirecao() != ESQUERDA) { setDirecao(DIREITA); }
					else { setDirecao(BAIXO); }
				}

				if (stringId.equals(vetorIds[3]) || stringId.equals(vetorIds[8]) || stringId.equals(vetorIds[9]) || stringId.equals(vetorIds[10])) {
					double rand3 = Math.random();
					if (rand3 <= 0.33 && getDirecao() != BAIXO) { setDirecao(CIMA); }
					else if (rand3 > 0.33 && rand3 <= 0.66 && getDirecao() != CIMA) { setDirecao(BAIXO); }
					else { setDirecao(DIREITA); }
					spreadInicial = true;
				}

				if (stringId.equals(vetorIds[11]) || stringId.equals(vetorIds[12]) || stringId.equals(vetorIds[13]) || stringId.equals(vetorIds[14])) {
					double rand4 = Math.random();
					if (rand4 <= 0.33 && getDirecao() != BAIXO) { setDirecao(CIMA); }
					else if (rand4 > 0.33 && rand4 <= 0.68 && getDirecao() != CIMA) { setDirecao(BAIXO); }
					else { setDirecao(ESQUERDA); }
				}
			}

			if (controleDeTurno == 40) {
				spreadInicial = true;
			}

		}


		// Algorítimo de movimentação para os agentes evitarem andarem encostados nas extremidades (bordas) da arena. Considerando as
		// constantes LARGURA_TELA = 900 e ALTURA_TELA = 700.

		if (getX() <= 30 && getDirecao() == ESQUERDA && !recebendoEnergia && !buscandoCogumelo && !ordemParaFicarParado) {
			double redirect1 = Math.random();
			if (redirect1 <= 0.34) { setDirecao(CIMA); }
			else if (redirect1 > 0.34 && redirect1 <= 0.68) { setDirecao(BAIXO); }
			else { setDirecao(DIREITA); }
		}

		else if (getY() <= 30 && getDirecao() == CIMA && !recebendoEnergia && spreadInicial && !buscandoCogumelo && !ordemParaFicarParado) {
			double redirect2 = Math.random();
			if (redirect2 <= 0.34) { setDirecao(ESQUERDA); }
			else if (redirect2 > 0.34 && redirect2 <= 0.68) { setDirecao(DIREITA); }
			else { setDirecao(BAIXO); }
		}

		else if (getX() >= 825 && getDirecao() == DIREITA && !recebendoEnergia && spreadInicial && !buscandoCogumelo && !ordemParaFicarParado) {
			double redirect3 = Math.random();
			if (redirect3 <= 0.34) { setDirecao(CIMA); }
			else if (redirect3 > 0.34 && redirect3 <= 0.68) { setDirecao(BAIXO); }
			else { setDirecao(ESQUERDA); }
		}

		else if (getY() >= 630 && getDirecao() == BAIXO && !recebendoEnergia && spreadInicial && !buscandoCogumelo && !ordemParaFicarParado) {
			double redirect4 = Math.random();
			if (redirect4 <= 0.34) { setDirecao(ESQUERDA); }
			else if (redirect4 > 0.34 && redirect4 <= 0.68) { setDirecao(DIREITA); }
			else { setDirecao(CIMA); }
		}

		if (controleDeTurno >= 90 && controleDeTurno % 30 == 0 && !recebendoEnergia && !buscandoCogumelo && !ordemParaFicarParado) { // Muda de direção a cada 30 turnos, a partir do nonagésimo turno.
			setDirecao(geraDirecaoAleatoria());
		}

		if (controleDeTurno > 45 && !buscandoCogumelo && !recebendoEnergia && !ordemParaFicarParado) { // Deixa o agente mais lento para poupar energia.
			para();
			if (controleDeTurno % 3 == 0) {
				setDirecao(getDirecao());
			}

		}

		if (!podeMoverPara(getDirecao()) && !recebendoEnergia && !buscandoCogumelo && !ordemParaFicarParado) {
			setDirecao(geraDirecaoAleatoria());
		}

		if (getEnergia() < 300 && !buscandoCogumelo) { // Faz o agente ficar parado para poupar energia.
			para();
		}

		if (ordemParaFicarParado && controleDeTurno % 3 == 0) {
			para();
		}

		if (emCombate) { // Para controlar o número de turnos em que o agente está em combate e se o mesmo não está mais combatendo.
			controleCombate += 1;
		}

		if (emCombate && controleCombate > turnosEmCombate + 2) { // Para controlar o número de turnos em que o agente está em combate e se o mesmo não está mais combatendo.
			emCombate = false;
			controleCombate = 0;
			turnosEmCombate = 0;
			setDirecao(geraDirecaoAleatoria());
		}


		if (recebendoEnergia) { // Para controlar o número de turnos em que o agente recebeu energia e se não está mais recebendo.
			controleEnergia += 1;
		}

		if ((controleEnergia > turnosQueRecebeuEnergia + 3) && recebendoEnergia && !ordemParaFicarParado) { // Para controlar o número de turnos em que o agente recebeu energia e se não está mais recebendo.
			recebendoEnergia = false;
			controleEnergia = 0;
			turnosQueRecebeuEnergia = 0;
			setDirecao(geraDirecaoAleatoria());
		}

		if (buscandoCogumelo) { // Para avisar que o agente não está mais buscando o cogumelo cujas coordenadas foram passadas por enviaMensagem().
			controleBuscaCogumelo += 1;
			if ((controleBuscaCogumelo + 3) > turnosQueBuscouCogumelo) {
				buscandoCogumelo = false;
			}
		}
	}
	
	public void recebeuEnergia() {
		para();
		turnosQueRecebeuEnergia += 1;
		recebendoEnergia = true;
		enviaMensagem("cogumelo encontrado posicao " + getX() + " " + getY()); // Avisa onde está o cogumelo.

	}
	
	public void tomouDano(int energiaRestanteInimigo) {
		if (modoLutador) { // Faz o agente lutar até morrer se o mesmo foi eleito um lutador e se não houverem cogumelos por perto.
			if ((getEnergia()-50) >= energiaRestanteInimigo) {
				para();
				turnosEmCombate += 1;
				emCombate = true;
			}
		} else {
			setDirecao(geraDirecaoAleatoria());
		}
	}
	
	public void ganhouCombate() { // Não consegui pensar em nada de útil para ser implementado aqui.
	}
	
	public void recebeuMensagem(String msg) {

		String mensagem[] = msg.split(" ");
		
		if (mensagem.length == 2) { // Interpreta a mensagem onde contém os IDs dos agentes e concatena todos os IDs dos agentes em uma string.
			if (mensagem[0].equals("meuid")) {
				stringRaw += mensagem[1] + " ";
			}
		}

		if (mensagem.length == 2) {
			if (mensagem[0].equals("pare")){ // Interpreta a mensagem pedindo pro agente ficar parado. Somente o agente cujo ID etá contido na primeira posição do vetor de IDs obedecerá a esse comando.
				String temp = String.valueOf(getId());
				if (mensagem[1].equals(temp)) {
					para();
					ordemParaFicarParado = true;
				}
			}
		}

		if (mensagem.length == 7) { // Elege os agentes das posições 1, 2 e 3 do vetor de IDs para se tornarem "lutadores".
			String temp = String.valueOf(getId());
			if (temp.equals(mensagem[4]) || temp.equals(mensagem[5]) || temp.equals(mensagem[6])) {
				modoLutador = true;
			}
		}


		if (mensagem.length == 5) { // Recebe as coordenadas da localização do cogumelo e direciona os agentes.
			if (!recebendoEnergia && !buscandoCogumelo && !ordemParaFicarParado) {
				turnosQueBuscouCogumelo += 1;
				buscandoCogumelo = true;
				if (getX() < Integer.parseInt(mensagem[3])) {
					setDirecao(DIREITA);
				} else if (getX() > Integer.parseInt(mensagem[3])) {
					setDirecao(ESQUERDA);
				}
				if (getY() < Integer.parseInt(mensagem[4])) {
					setDirecao(BAIXO);
				} else if (getY() > Integer.parseInt(mensagem[4])) {
					setDirecao(CIMA);
				}
			}
		}
	}
	
	public String getEquipe() {
		return "IMF_Agent";
	}

}