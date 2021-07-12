package br.uffs.cc.jarena;

public class IMF_Agent extends Agente
{
	public int agentesProntos = 0;
	public int controleDeTurno;
	public int controleEnergia;
	public int turnosQueRecebeuEnergia;
	public int startingX = 0;
	public int startingY = 0;
	public double random;
	public String stringRaw = "";
	public String vetorIds[];
	public boolean buscandoCogumelo = false;
	public boolean chegouNoMeio = false;
	public boolean cond = true;
	public boolean energiaCritica = false;
	public boolean formacaoInicial = false;
	public boolean ordemParaFicarParado = false;
	public boolean primeiroMovimento = false;
	public boolean recebendoEnergia = false;


	public IMF_Agent(Integer x, Integer y, Integer energia) {

		super(x, y, energia);
		setDirecao(geraDirecaoAleatoria());

	}
	
	@Override
	public void pensa() {

		controleDeTurno += 1; // Variável para o controle de várias ações.

		random = Math.random();

		if (controleDeTurno == 1) { // Agentes enviam mensagens uns para os outros contendo seus IDs para serem armazenados em um vetor.
			enviaMensagem("meuid " + getId());
		}

		if (controleDeTurno == 2) {

			vetorIds = stringRaw.split(" ");  // Quebra a string contendo os IDs dos agentes.

			if (getX() < 150 && getY() < 150) { // Identifica qual o canto da arena em que os agentes nasceram e define o ponto central da formação.
				startingX = 110;
				startingY = 110;
			}
	
			else if (getX() > 710 && getY() < 150) {
				startingX = 750;
				startingY = 110;
			}
	
			else if (getX() < 150 && getY() > 510) {
				startingX = 110;
				startingY = 550;
			}
	
			else if (getX() > 710 && getY() > 510) {
				startingX = 750;
				startingY = 550;
			}

		}

		if (controleDeTurno >= 3 && (getId() == Integer.parseInt(vetorIds[13]) || getId() == Integer.parseInt(vetorIds[14]))) { // define dois agentes que ficaram de fora da formação para ficarem parados, garantindo o máximo de tempo de sobrevivência possível
			ordemParaFicarParado = true;
		}

		if (ordemParaFicarParado && controleDeTurno % 4 == 0) {
			para();
		}

		if (controleDeTurno >= 3 && !formacaoInicial) { // Direciona cada agente para seu ponto inicial na formação.

			if (getId() == Integer.parseInt(vetorIds[0])) { // Agente líder
				definePontoInicial(getId(), startingX, startingY);
			}

			else if (getId() == Integer.parseInt(vetorIds[1])) {
				definePontoInicial(getId(), startingX - 50, startingY);
			}

			else if (getId() == Integer.parseInt(vetorIds[2])) {
				definePontoInicial(getId(), startingX - 100, startingY);
			}

			else if (getId() == Integer.parseInt(vetorIds[3])) {
				definePontoInicial(getId(), startingX + 50, startingY);
			}

			else if (getId() == Integer.parseInt(vetorIds[4])) {
				definePontoInicial(getId(), startingX + 100, startingY);
			}

			else if (getId() == Integer.parseInt(vetorIds[5])) {
				definePontoInicial(getId(), startingX - 50, startingY - 50);
			}

			else if (getId() == Integer.parseInt(vetorIds[6])) {
				definePontoInicial(getId(), startingX, startingY - 50);
			}

			else if (getId() == Integer.parseInt(vetorIds[7])) {
				definePontoInicial(getId(), startingX + 50, startingY - 50);
			}

			else if (getId() == Integer.parseInt(vetorIds[8])) {
				definePontoInicial(getId(), startingX, startingY - 100);
			}

			else if (getId() == Integer.parseInt(vetorIds[9])) {
				definePontoInicial(getId(), startingX - 50, startingY + 50);
			}

			else if (getId() == Integer.parseInt(vetorIds[10])) {
				definePontoInicial(getId(), startingX, startingY + 50);
			}

			else if (getId() == Integer.parseInt(vetorIds[11])) {
				definePontoInicial(getId(), startingX + 50, startingY + 50);
			}

			else if (getId() == Integer.parseInt(vetorIds[12])) {
				definePontoInicial(getId(), startingX, startingY + 100);
			}

		}

		if (getX() == 430 && getY() == 330) {
			chegouNoMeio = true;
		}

		if (controleDeTurno > 4 && (getId() == Integer.parseInt(vetorIds[0]))) { // Bloco de comandos que só o agente líder (o agente do centro da formação) executará.

			if (getEnergia() <= 200 && !energiaCritica && !buscandoCogumelo) { // Todos os agentes param se o líder estiver com menos de 200 de energia.
				energiaCritica = true;
				enviaMensagem("pare");
				para();
			}
			if (agentesProntos == 13 && !buscandoCogumelo && !energiaCritica) { // Faz o primeiro movimento da formação e faz com que a mesma alterne entre estar andando e estar parado a cada 20 turnos.
				if (controleDeTurno % 20 == 0 || !primeiroMovimento) {
					if (cond) {
						if (!chegouNoMeio) { // Direciona toda a formação ao centro da arena, visto que é mais próximo ao centro que essa estratégia da formação funciona melhor. Após chegar no centro, direções aleatórias são geradas.
							enviaMensagem("direcao " + String.valueOf(moveParaPosicao(430, 330)));
						} else {
							int temp = geraDirecaoAleatoria();
							enviaMensagem("direcao " + String.valueOf(temp));
							setDirecao(temp);
							cond = false;
							primeiroMovimento = true;
						}
					} else {
						enviaMensagem("pare");
						para();
						cond = true;
					}
				}
			}

			if (getX() <= 110 && getDirecao() == ESQUERDA) { // Faz o agente líder se redirecionar caso esteja indo de encontro com uma das bordas da arena, para evitar a quebra da formação.
				enviaMensagem("direcao " + String.valueOf(novaDirecao(CIMA, BAIXO, DIREITA)));
			}
	
			else if (getY() <= 110 && getDirecao() == CIMA) {
				enviaMensagem("direcao " + String.valueOf(novaDirecao(ESQUERDA, DIREITA, BAIXO)));
			}
	
			else if (getX() >= 750 && getDirecao() == DIREITA) {
				enviaMensagem("direcao " + String.valueOf(novaDirecao(CIMA, BAIXO, ESQUERDA)));
			}
	
			else if (getY() >= 550 && getDirecao() == BAIXO) {
				enviaMensagem("direcao " + String.valueOf(novaDirecao(ESQUERDA, DIREITA, CIMA)));
			}

			if (recebendoEnergia) { // Faz a formação parar caso o agente líder esteja recebendo energia.
				controleEnergia++;
				enviaMensagem("pare");
				para();

				if (controleEnergia - 1 > turnosQueRecebeuEnergia) { // Serve para detectar que o agente líder não está mais recebendo energia e está livre para enviar novos comandos de movimento.
					recebendoEnergia = false;
					controleEnergia = 0;
					turnosQueRecebeuEnergia = 0;
				}
			}

		}

		if (buscandoCogumelo) { // Isso ajuda, de alguma forma, evitar que o agente líder fique dessincronizado com o restante da formação (ajuda, mas não garante).
			buscandoCogumelo = false;
		}
	}
	
	@Override
	public void recebeuEnergia() {

		if (getId() != Integer.parseInt(vetorIds[0]) && agentesProntos == 13 && !ordemParaFicarParado) { // Agentes que não são o líder enviam a localização dos cogumelos.
			enviaMensagem("cogumelo " + getX() + " " + getY());
		}

		if (getId() == Integer.parseInt(vetorIds[0])) { // Sinaliza para que o agente líder não envie mais comandos de movimento enquanto estiver recebendo energia.
			recebendoEnergia = true;
			turnosQueRecebeuEnergia++;
		}

	}
	
	@Override
	public void tomouDano(int energiaRestanteInimigo) {
		if (ordemParaFicarParado) {
			setDirecao(geraDirecaoAleatoria());
		}

	}
	
	@Override
	public void ganhouCombate() {
		 // Não consegui pensar em nada de útil para ser implementado aqui.
	}
	
	@Override
	public void recebeuMensagem(String msg) {

		String mensagem[] = msg.split(" ");
		
		if (mensagem[0].equals("meuid")) { // Interpreta a mensagem onde contém os IDs dos agentes e concatena todos os IDs dos agentes em uma string.
			stringRaw += mensagem[1] + " ";
		}

		if (mensagem[0].equals("pare")) { // Interpreta a mensagem pedindo pro agente ficar parado.
			para();
		}

		if (mensagem[0].equals("pronto") && agentesProntos < 13) { // Os agentes que se colocarem na formação avisam o agente líder que já estão alinhados.
			agentesProntos++;
		}

		if (mensagem[0].equals("direcao") && getId() != Integer.parseInt(vetorIds[0]) && !ordemParaFicarParado) { // Comando de direção.
			setDirecao(Integer.parseInt(mensagem[1]));
		}

		if (mensagem[0].equals("cogumelo") && getId() == Integer.parseInt(vetorIds[0])) {
			buscandoCogumelo = true;
			int tempX = Integer.parseInt(mensagem[1]);
			int tempY = Integer.parseInt(mensagem[2]);
			if (tempX >= 750 || tempX <= 110 || tempY >= 550 || tempY <= 110) { // O agente líder não vai diretamente de encontro ao cogumelo caso ele esteja perto demais das bordas, para evitar a quebra da formação.
				enviaMensagem("pare");
				para();
			} else {
				if (!recebendoEnergia) { // O agente líder recebe as coordenadas do cogumelo, e começa a se movimentar em direção ao mesmo. Ao definir uma direção, o agente líder envia essa mesma direção como comando para todos os agentes permanecerem na formação.
					enviaMensagem("direcao " + String.valueOf(moveParaPosicao(Integer.parseInt(mensagem[1]), Integer.parseInt(mensagem[2]))));
				}
			}
		}

	}

	@Override
	public String getEquipe() {
		return "IMF_Agent";
	}

	public int moveParaPosicao(int x, int y) { // Define uma direção e a retorna para o agente líder repassar para os outros agentes.
		if (controleDeTurno % 2 == 0) { // Essa condição do módulo da divisão por 2 é uma lógica para fazer os agentes alternarem entre dar um passo no eixo X, e outro no eixo Y, para perseguir o cogumelo com mais eficácia.
			if (getX() < x) {
				setDirecao(DIREITA);
				return DIREITA;
			} else if (getX() > x) {
				setDirecao(ESQUERDA);
				return ESQUERDA;
			} else {
				return getDirecao();
			}
		} else if (controleDeTurno % 2 != 0) {
			if (getY() < y) {
				setDirecao(BAIXO);
				return BAIXO;
			} else if (getY() > y) {
				setDirecao(CIMA);
				return CIMA;
			} else {
				return getDirecao();
			}
		} else {
			return getDirecao();
		}
	}

	public int novaDirecao(int dir1, int dir2, int dir3) { // Função que põe em prática a questão do redirecionamento para evitar a quebra da formação.
		random = Math.random();
		if (random <= 0.34) { setDirecao(dir1); return dir1; }
		else if (random > 0.34 && random <= 0.68) { setDirecao(dir2); return dir2; }
		else { setDirecao(dir3); return dir3; }
	}

	public void definePontoInicial(int id, int x, int y) { // Direciona os agentes para seus respectivos pontos iniciais e avisa o agente líder que estão alinhados.
		if (getX() == x && getY() == y) {
			para();
			enviaMensagem("pronto");
			formacaoInicial = true;
		} else {
			moveParaPosicao(x, y);
		}
	}
}