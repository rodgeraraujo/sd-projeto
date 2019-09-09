## gRPC e RMI são os mecanismos que possibilitam ao cliente invocar o procedimento ou 
método do servidor através de comunicação entre cliente e servidor.

## As principais diferenças entre os dois são:

* O gRPC suporta paradigmas de programação procedural(baseado em C), já o RMI suporta paradigmas de programação orientados 
a objetos(baseado em java).

* No gRPC, devem ser passados parâmetros "in-out", ou seja, os valores de entrada e saída devem ter os mesmos tipos de dados. 
Já no RMI não há necessidade em passar parâmetros de entrada e saída.

* Os parâmetros passados para procedimentos remotos no RPC são estruturas de dados comuns. No RMI são passados objetos como 
parâmetro para o método remoto.

* No RPC, as referências não são prováveis porque os dois processos possuem espaço de endereço distinto, contrário ao RMI.

Portanto, os dois mecanismos têm o mesmo objetivo, mas são usados em linguagens que suportam diferentes paradigmas de 
programação, possuindo recursos distintos.

### O diagrama a seguir mostra as etapas de trabalho na implementação do RPC:

![RPC](https://techdifferences.com/wp-content/uploads/2017/12/RPC-mechanism.jpg)

### Arquitetura do cliente-servidor do protocolo RMI:

![RMI](https://techdifferences.com/wp-content/uploads/2017/12/RMI-mechanism2.jpg)
