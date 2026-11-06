#  DS_Trabs — Conversor de Texto em Música

Programa em Java que transforma um texto comum em música. Cada caractere digitado vira uma instrução musical (tocar uma nota, fazer uma pausa, mudar de instrumento, acelerar o andamento...), e o resultado pode ser ouvido na hora ou exportado como arquivo MIDI.

##  Sobre o trabalho

| | |
|---|---|
| **Disciplina** | Desenvolvimento de Software (INF01120) – UFRGS |
| **Integrantes** | Felipe Grehs · Guilherme Gamarra · Lucas Fuchs |
| **Professor** | Marcelo Pimenta |

##  Ideia do programa

A concepção do programa se deu por traduzir uma linguagem pré-determinada (fases 1 e 2 do trabalho) em música. O usuário é capaz de digitar (ou abrir um arquivo `.txt`) uma sequência de letras, símbolos e números, e o programa traduz cada um deles, seguindo regras de mapeamento, para a sintaxe de música da biblioteca [JFugue](http://www.jfugue.org/), que então toca o resultado.

Por exemplo, `C:3D:3E:3F:3G:1` toca as notas Dó, Ré, Mi, Fá e Sol, e `C;C` toca um Dó, troca de instrumento e toca outro Dó.

### Funcionalidades

- Tradução de texto em música com **notas, sustenidos, bemóis e 7 durações diferentes**
- Controle de **oitava, volume, andamento (BPM) e instrumento** direto no texto ou pelos controles da tela
- Catálogo com os **128 instrumentos General MIDI**
- **Até 4 vozes** simultâneas (cada linha do texto é uma voz)
- **Atraso** de uma voz em relação às outras
- Abrir, editar e salvar músicas em arquivos `.txt`
- **Exportar para MIDI** (`.mid`)
- **Playlists** com modo aleatório e repetição, organizadas em um álbum
- Alteração do **BPM em tempo real**, enquanto a música toca
- Barra de progresso e controles de reprodução (play/pause, parar, anterior, próxima)

---

##  Como rodar

### Pré-requisitos

- **JDK 17 ou superior** (o código usa recursos de versões recentes do Java)
- A biblioteca **JFugue 5.0.9**, que já está incluída em [`lib/jfugue-5.0.9.jar`](lib/jfugue-5.0.9.jar)

É possível conferir sua versão com `java -version` e `javac -version`.

### 1. Clonar o repositório

```bash
git clone https://github.com/SEU_USUARIO/DS_Trabs.git
cd DS_Trabs
```

### 2. Rodar pela IDE (mais fácil)

**IntelliJ IDEA**
1. `File → Open` e selecione a pasta do projeto.
2. Em `File → Project Structure → Libraries`, adicione o `lib/jfugue-5.0.9.jar`.
3. Abra o `Main.java` e clique em ▶ **Run**.

**VS Code** (com o *Extension Pack for Java*)
1. `File → Open Folder` e selecione a pasta do projeto.
2. O `lib/` costuma ser reconhecido automaticamente como biblioteca.
3. Abra o `Main.java` e clique em **Run**.

### 3. Rodar pelo terminal

Execute sempre **a partir da pasta raiz do projeto** (a que contém `ui/`, `lib/` e `Main.java`). Isso é necessário para a imagem de fundo (`ui/oceano.gif`) ser encontrada.

**Linux / macOS**
```bash
javac -cp lib/jfugue-5.0.9.jar -d bin $(find . -name "*.java" -not -path "./out/*")
java -cp "bin:lib/jfugue-5.0.9.jar" Main
```

**Windows (PowerShell)**
```powershell
$fontes = Get-ChildItem -Recurse -Filter *.java | Where-Object { $_.FullName -notmatch '\\out\\' } | ForEach-Object { $_.FullName }
javac -cp lib/jfugue-5.0.9.jar -d bin $fontes
java -cp "bin;lib/jfugue-5.0.9.jar" Main
```

>  **Som:** o programa usa o sintetizador MIDI do Java. Se não houver áudio, verifique o volume do sistema e se o dispositivo de saída está correto.

---

##  Como usar

### Primeira música em 4 passos

1. Abra o programa. No centro da tela, clique na caixa **"Clique para expandir a caixa"**.
2. Digite uma música, por exemplo: `C:3D:3E:3F:3G:3A:3B:3?C:3`
3. Escolha o instrumento inicial na barra inferior (opcional).
4. Clique no botão **▶** (play) para ouvir.

> Dica: A pasta [`testes/`](testes/) tem músicas prontas para abrir pelo botão **Abrir TXT**: *Brilha Brilha Estrelinha, Jingle Bells, Noite Feliz, Parabéns, escalas*, entre outras.

### A interface

| Região | O que tem |
|---|---|
| **Esquerda — "Sua Biblioteca"** | Seletor de playlist, botão **+ Playlist**, lista das músicas, botões **+ Adicionar**, **− Remover** e **Tocar Playlist** |
| **Centro** | Botões **Abrir TXT**, **Salvar**, **Salvar Como** e **Exportar MIDI**, e a caixa de texto onde a música é escrita |
| **Barra inferior** | Instrumento inicial, 🔀 aleatório, ⏮ anterior, ▶/⏸ play/pause, ⏹ parar, ⏭ próxima, 🔁 repetir, barra de progresso e os sliders de **Oitava**, **BPM** e **Volume** |

### Controles da barra inferior

| Controle | Faixa | Padrão |
|---|---|---|
| Oitava | 0 a 9 | 6 |
| BPM (andamento) | 20 a 300 | 120 |
| Volume | 0 a 100 | 100 |
| Instrumento inicial | 128 instrumentos GM | Piano Acústico |

Os valores escolhidos valem para a **primeira voz** e também para a exportação em MIDI. Mudar o **BPM** com a música tocando altera a velocidade na hora.

### Playlists

- Cada playlist guarda **até 10 músicas** (arquivos `.txt`), e o álbum guarda **até 3 playlists**.
- Em **+ Adicionar** dá para selecionar vários arquivos de uma vez.
- Clicar em uma música da lista carrega o texto dela na caixa.
- **Tocar Playlist** toca em sequência a partir da música selecionada. Com 🔀 a ordem é embaralhada, e com 🔁 a playlist recomeça quando termina.

### Exportar MIDI

Clique em **Exportar MIDI**, escolha o nome e a pasta. O arquivo `.mid` é gerado com o texto, o instrumento, a oitava, o BPM e o volume que estiverem na tela naquele momento.

---

##  Ideia implementada de funcionalidade

### Visão geral

O texto passa por um "pipeline" de tradução. Cada linha do texto é uma voz, e cada voz é dividida em pedaços. Para cada pedaço é procurada a primeira regra que sabe tratá-lo através da biblioteca regex (reconhecimento de padrões no texto de entrada). Cada regra da nossa aplicação implementa uma interface `analisaChar`, que reconhece o padrão e executa o mapeamento.Outro elemento fundamental do nosso projeto é o `ParametrosAtuais`, que guarda o estado de cada voz enquanto o texto é lido, tais como oitava, volume, instrumento, BPM e a última nota tocada. Com isso. é possível realizar as conversões de texto para a formatação reconhecida pelo JFugue.

### Tabela de mapeamento

| Entrada | O que faz | Detalhes |
|---|---|---|
| `A B C D E F G` (maiúsculas) | **Toca a nota** | Na oitava, volume e duração atuais. Fica `Lá, Si, Dó, Ré, Mi, Fá, Sol` |
| `#` ou `b` logo após a nota | Sustenido / bemol | `C#`, `Bb`, `F#` |
| `:1` a `:7` logo após a nota | **Define a duração** | Veja a tabela de durações abaixo. Sem `:`, a duração é a semínima |
| `a b c d e f g` (minúsculas) | **Pausa** | Silêncio de uma semínima |
| **Espaço** | **Dobra o volume** | Máximo de 127 |
| `.` ou `?` | **Sobe uma oitava** | Ao passar da oitava 9, volta para a oitava inicial da voz |
| `V` (maiúsculo) | **Desce uma oitava** | Ao passar da oitava 0, volta para a oitava inicial da voz |
| `>` | **Aumenta o andamento** | +10 BPM (máximo 300) |
| `<` | **Diminui o andamento** | −10 BPM (mínimo 20) |
| `!` | Troca para **Bandoneon** | Instrumento GM nº 24 |
| `,` | Troca para **Agogô** | Instrumento GM nº 114 |
| `;` e dígitos ímpares `1 3 5 7 9` | Troca para **Tubular Bells** | Instrumento GM nº 15 |
| Dígitos pares `0 2 4 6 8` | **Soma ao instrumento atual** | Soma o valor do dígito ao número do instrumento. Passou de 128, volta ao começo |
| **Qualquer outro caractere** | **Repete a última nota** ou faz **pausa** | Se o caractere anterior foi uma nota, repete a nota. Senão, faz uma pausa |
| `[n]` no início de uma linha | **Atraso** | Insere `n` pausas antes da voz. Ex.: `[3]CDE` |

> ATENÇÃO: O espaço **sempre dobra o volume**, então vários espaços seguidos deixam tudo no volume máximo. Para separar notas sem mexer no volume, escreva-as coladas (`CDEF`) ou use pausas (`a`–`g`).

### Convenções de durações adotadas

| Código | Duração | Letra JFugue |
|---|---|---|
| `:1` | Semibreve | `w` |
| `:2` | Mínima | `h` |
| `:3` | Semínima (**padrão**) | `q` |
| `:4` | Colcheia | `i` |
| `:5` | Semicolcheia | `s` |
| `:6` | Fusa | `t` |
| `:7` | Semifusa | `x` |

### Vozes (linhas do texto)

Cada **linha** é uma voz diferente, tocada ao mesmo tempo. São permitidas **até 4 linhas**. Mais que isso gera o erro *"O texto tem N linhas, mas o máximo é 4 vozes"*.

| Voz | Oitava | Volume | Instrumento |
|---|---|---|---|
| 1ª linha | a escolhida (padrão 6) | o escolhido | o escolhido na barra inferior |
| 2ª linha | escolhida − 1 | escolhido − 20 | Órgão (nº 20) |
| 3ª linha | escolhida − 2 | escolhido − 40 | Piano Acústico (nº 1) |
| 4ª linha | escolhida − 3 | escolhido − 60 | Fagote (nº 71) |

Os valores nunca ficam abaixo de 0. Cada voz pode trocar de instrumento, oitava e volume por conta própria durante a música.

### Exemplos de tradução

Com os valores padrão (BPM 120, volume 100, oitava 6, Piano Acústico):

| Texto | Saída JFugue | O que acontece |
|---|---|---|
| `C#:2Bb` | `T120 V0 I0 C#6ha100 Bb6qa100` | Dó sustenido de 2 tempos (mínima) e Si bemol de semínima |
| `C@@` | `T120 V0 I0 C6qa100 C6qa100 Rq` | O 1º `@` repete a nota, o 2º `@` vira pausa |
| `CDE>F` | `T120 V0 I0 C6qa100 D6qa100 E6qa100 T130 F6qa100` | O `>` acelera de 120 para 130 BPM |
| `[2]C` | `T120 V0 I0 Rq Rq C6qa100` | Duas pausas de atraso e depois a nota |
| `C;C` | `T120 V0 I0 C6qa100 I14 C6qa100` | O `;` troca para Tubular Bells |
| `C` + nova linha + `E` | `T120 V0 I0 C6qa100 V1 I19 E5qa80` | A 2ª linha vira a voz 1, com Órgão, uma oitava abaixo e volume 80 |

Como ler a saída: `T120` é o tempo (BPM), `V0` é a voz, `I0` é o instrumento (numeração do JFugue começa em 0, então `I0` é o instrumento nº 1), `C6qa100` é a nota Dó na oitava 6, duração `q` e intensidade 100, e `Rq` é uma pausa de semínima.

---

##  Organização do código

```
DS_Trabs/
├── Main.java                 # Ponto de entrada: cria o Tradutor e abre a janela
├── lib/
│   └── jfugue-5.0.9.jar      # Biblioteca de música (JFugue)
│
├── io/
│   └── LeitorTxt.java        # Leitura e gravação de arquivos .txt
│
├── musica/                   # Núcleo: tradução de texto para música
│   ├── Tradutor.java         # Orquestra a tradução: separa vozes e aplica as regras
│   ├── Selecionador.java     # Quebra a linha em "pedaços" (ex.: "C#:2", "a", ";")
│   ├── ContextoChar.java     # Um pedaço e o seu caractere principal
│   ├── ParametrosAtuais.java # Estado de uma voz (oitava, volume, instrumento, BPM...)
│   ├── Vozes.java            # Valores iniciais de cada uma das 4 vozes
│   ├── Duracao.java          # Converte ":1".. ":7" em letras de duração
│   ├── FormatacaoJF.java     # Monta notas, pausas e trocas de instrumento em JFugue
│   └── Instrumento.java      # Catálogo dos 128 instrumentos General MIDI
│
├── regras/                   # Uma classe por tipo de comando
│   ├── analisaChar.java      # Interface que toda regra implementa
│   ├── InicializaRegras.java # Monta a lista de regras na ordem de prioridade
│   ├── Nota.java             # A–G: toca a nota
│   ├── Silencio.java         # a–g: pausa
│   ├── TrocaInstrumento.java # ! , ; e dígitos: muda de instrumento
│   ├── Volume.java           # Espaço: dobra o volume
│   ├── Oitava.java           # . ? V: sobe ou desce a oitava
│   ├── Andamento.java        # > <: muda o BPM
│   └── RegraDefault.java     # Qualquer outro caractere: repete a nota ou pausa
│
├── playlist/
│   ├── ListaPlaylist.java    # Playlist (até 10 músicas, aleatório, loop, fila)
│   └── Album.java            # Álbum que reúne até 3 playlists
│
├── ui/                       # Interface gráfica (Swing)
│   ├── Janela.java           # Janela principal: editor, playlists e controles do player
│   ├── Tema.java             # Cores, fontes e criação dos componentes estilizados
│   ├── Cartao.java           # Painel com bordas arredondadas e fundo translúcido
│   ├── PainelOceano.java     # Fundo com o GIF animado do oceano
│   ├── BarraProgresso.java   # Barra de progresso da música
│   └── oceano.gif
│
└── testes/                   # Músicas de exemplo em .txt
```


### Decisões de projeto

- **Padronização das regras:** Cada comando é uma classe que implementa `analisaChar` (`TesteChar` diz se a regra se aplica, `TraducaoTexto` produz a música). Para criar um comando novo basta criar uma classe e registrá-la em `InicializaRegras`.
- **Lista de regras:** O `Tradutor` usa a primeira regra que reconhecer o caractere, e por isso a `RegraDefault` (que aceita tudo) precisa ser **sempre** a última da lista.
- **Estado separado do comportamento.** As regras não guardam nada. Tudo o que precisa ser lembrado (última nota, oitava, volume...) fica em `ParametrosAtuais`, um para cada voz.
- **Interface separada da lógica.** O pacote `musica` não depende do Swing, então a tradução pode ser usada e testada sem abrir nenhuma janela.

---

## O que foi utilizado

- **Java** (JDK 17+)
- **Swing** para a interface gráfica
- **[JFugue 5.0.9](http://www.jfugue.org/)** para tocar e exportar música (MIDI)
- **Git / GitHub** para versionamento

## 👥 Autores

- **Felipe Grehs**
- **Guilherme Gamarra**
- **Lucas Fuchs**
