# 3 - Questão (Garantia de Entrega)

Analise o código da última questão (2) e avalie o que problemas podem ocorrer e quais soluções podem ser adotadas para
resolver o problema e que implicações podem levar esta solução.

## O servidor cai durante o processamento de uma mensagem:

* Problemas:

Este cenário trata de uma falha crítica, 
o crash do servidor. Se o processamento foi executado e, portanto, não é possível executá-la de novo, assim ocorrendo a 
perda da mensagem.

* Soluções:

As possíveis soluções vão depender do que é esperado pelo cliente. Se ele foi projetado utilizando da semântica at-least-once,
o cliente continua tentando até que uma resposta seja recebida, nesse caso o RPC será executado ao menos uma vez.
Utilizando a semântica at-most-once, nesse caso o cliente desiste após a primeira tentativa falha e retorna o status de 
falha, nesse caso o RPC será executado no máximo uma única vez, mas provavelmente nenhuma.
Ou utilizando da semântica exactly-once, a chamada é executada uma única vez.

## O cliente cai antes de receber uma resposta:

* Problemas:

Quando um cliente pára de operar, as solicitações dele que estavam sendo processadas perdem o sentido, pois não há ninguém 
aguardando o resultado e o servidor está mantendo recursos em vão. Estas solicitações são chamadas de órfãs e precisam ser 
tratadas, visto que desperdiçam tempo de processador e podem estar executando bloqueios em alguns recursos de processamento.

* Soluções:

Uma solução seria tentar tornar as operações idempotentes, ou seja, repetível sem efeitos indesejados. Outra solução seria 
escrever um log de cada chamada RPC, a fim de ser possível desfazê-la, se necessário; na prática, isto leva à perda de 
desempenho e poderia requerer um espaço considerável no disco, já que o número de RPCs em andamento em um sistema é grande, 
principalmente por RPC ser bastante usado em arquitetura de micro serviços.

## Adotar a técnica de recuperação de mensagem push notification ao invés de pulling:

Pulling, é um estilo de comunicação de rede em que a solicitação inicial de dados se origina do cliente e, em seguida,
é respondida pelo servidor. O inverso é conhecido como push notifications, em que o servidor envia dados aos clientes 
interessados em recebê-los.

Ao utilizar push notification como estilo de comunicação, se torna desnecessário a solicitação frequente por novos dados, 
já que os clientes interessados(inscritos nos canais) irão ser notificados sempre que houver algo novo. Isso otimiza o 
desempenho das aplicações e das notificações, pois o processamento de solicitação usado no pulling será destinado para 
outros fins.
