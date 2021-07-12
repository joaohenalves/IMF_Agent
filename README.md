# IMF_Agent

 Aluno: João Henrique Alves

 Matrícula: 1911100043

 # Explicação da estratégia:

A estratégia escolhida consiste em agrupar os agentes em uma formação como uma estrela,
e definir um agente líder que comandará todos os agentes. No início, cada agente se posiciona em sua respectiva
posição na formação e assim que todos estiverem prontos, o agente líder passará a enviar comandos de movimento para todos os agentes.
Toda a formação está orientada na lógica de que, ao encontrar um cogumelo, fazer com que o agente líder receba a energia. Se o cogumelo começar a se distanciar, a formação será capaz de detectar a direção em que o cogumelo está indo e continuar o perseguindo. Após a formação ser iniciada, os agentes são direcionados ao centro da arena visto que é próximo ao centro que essa estratégia é mais eficaz. Os agentes trocam informações entre si, tal como seus IDs. Cada instância da classe constrói o seu próprio vetor de IDs que no fim das contas acaba sendo igual em todos os agentes, sem utilizar propriedades estáticas. Por fim, há dois agentes que ficam de fora da formação, sendo estes orientados a permanecerem parados durante a batalha para assegurar o máximo de tempo de sobrevivência possível.


























# Jarena
A game approach to teaching OPP and Java. It is a battle arena where student-developed agents fight each other in order to survive. The last team to be eliminated from the arena wins the challenge.

![jarena](https://cloud.githubusercontent.com/assets/512405/9395397/9c3e4b06-4764-11e5-9669-ba1775a00bdd.png)

## Motivation

Jarena was created as a helping tool to bring a game related environment to my OOP classes. Students receive a task that consists of programming a software agent (described as a Java class) and testing it in a battle environment represented by an arena.

For more information regarding this project, check the paper ***Jarena: experimenting a tool for teaching object-oriented programming
concepts using Java, games and software agents (BEVILACQUA, F. ; SEBBEN, A. ; TORCHELSEN, R. P; 2011)***.

## Installation

Download the [lastest version](https://github.com/Dovyski/Jarena/archive/master.zip) of Jarena.

Unzip the downloaded file and create a java class within the package `br.uffs.cc.jarena`. This class represents your agent in the battle field. Name it the way you want, e.g. `MyAgent.java`. This java class must be placed in the same directory of the remaining classes of Jarena (`src/br/uffs/cc/jarena`).

Make your agent be added to the area. For that change the file `/src/br/uffs/cc/jarena/Arena.java`, line 61, replacing the class `AgenteDummy` with your own agent. E.g.:

```
adicionaEntidade(new MyAgent(0, 0, Constants.ENTIDADE_ENERGIA_INICIAL));
```

Compile everything:

```
cd path/to/unzipped/jarena/folder
```
```
javac ./src/br/uffs/cc/jarena/*.java ./src/br/uffs/cc/jarena/renders/simple2d/*.java -d bin/
```

If you see no compiling errors, you are good to go to run the application:

```
cd bin
```
```
java br.uffs.cc.jarena.Main
```

When the application is running, you can terminate it by pressing `Q`. Use the `left`/`right` keys to adjust the speed of the simulation, if needed.

## Contributors

If you liked the project and want to help, you are welcome! Submit pull requests or [open a new issue](https://github.com/Dovyski/Jarena/issues) describing your idea.

## License

Jarena is licensed under the MIT license.
