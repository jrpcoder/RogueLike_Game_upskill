Projecto intercalar no âmbito do curso de linguagem em Java (Programa UPskill)

co-Autor:
José Pereira

Descrição:
O projecto centra-se na implementação de um jogo do tipo estratégia similar ao Rogue (http://en.wikipedia.org/wiki/Roguelike).

Demos:
- Ataque físico por contacto e apanhar objectos.
![Demo ataque físico e apanhar objectos](Rogue_demos/Rogue_demo_physicalAttack_catchItems.gif)
<img src="Rogue_demos/Rogue_demo_physicalAttack_catchItems.gif")

Mecanismos do jogo:
- O jogo inicia-se na room 0 ou em uma outra caso haja um save prévio.
- A cada movimento do personagem principal (Hero), os restantes inimigos também se movimentam.
- Os personagens atacam-se caso estejam lado a lado. Não há sobreposição. Os inimigos apenas atacam o hero e vice-versa.
- Cada inimigo tem um nivel de ataque diferente.
- Ao pressionar a tecla "Space", o Hero dispara bolas de fogo.
- A direcção das bolas de fogo é determinada pela ultima direcção do hero.
- o hero ganha pontos por cada item apanhado e inimigo eliminado. Perde também um ponto por cada movimento.
- Cada vez que o hero ganha pontos aparecerá na consola a nova pontuação do mesmo.
- Na implementação deste jogo, o heroi pode atravessar apenas pelas portas solidas e não pelas abertas, pois estas serão locais onde o hero irá ter quando passa uma porta solida.
- Pode ser necessário uma chave para passar uma porta solida. Essa indicação é dada pela tentativa de passagem e consequente insucesso (neste caso, uma chave será necessária).
- Uma chave só poderá ser apanhada caso haja espaço na barra dos items (lado direito da barra de vida).
- Não é possível largar uma chave. Quando utilizada, a mesma é removida.
- Ao pressionar a tecla "s", um novo save é gravado.
- Um save pode ser eliminado quando um outro é efectuado ou se o conteudo no ficheiro save.txt for apagado.
- Ao morrer, o hero voltará ao inicio caso não haja nenhum save. Caso contrário, reiniciará na sala indicada no save. A pontuação será a mesma da altura do save. É feito um reset a todos os atributos restantes, quer do hero, quer dos inimigos.
- Quando o hero morre é apresentado na consola o termo "GAME OVER" e é indicada a pontuação final. É também pedido o nickname do jogador de forma a ser criada uma leaderboard ordenada.
- A leaderboard pode ser observada no ficheiro leaderboard.txt. Para reiniciar a leaderboard basta eliminar o conteudo do mesmo ficheiro.
- O jogo termina quando o hero alcança a taça no último nível.
- Aqui, novamente na consola, é dada uma mensagem de parabéns ao jogador, a sua pontuação final e é pedido o seu nickname para gravar a pontuação na leaderboard.
