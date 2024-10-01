# Big Data e Cloud Computing
Repositório referente à disciplina de **Big Data e Cloud Computing** (2024.2).

**AP1**: Criação de um sistema de gerenciamento e associação entre clientes e endereços.

---
## Rotas (projeto)

---

## Cliente

### **Listar clientes**

**GET:** localhost:8080/cliente

**Descrição:** Retorna a lista de todos os clientes.

---

### **Obter cliente por ID**

**GET:** localhost:8080/cliente/{id}

**Descrição:** Retorna os detalhes do cliente correspondente ao ID fornecido.

---

### **Criar cliente**

**POST:** localhost:8080/cliente

**Descrição:** Cria um novo cliente.

---

### **Atualizar cliente**

**PUT:** localhost:8080/cliente/{id}

**Descrição:** Atualiza os detalhes do cliente correspondente ao ID fornecido.

---

### **Deletar cliente**

**DELETE:** localhost:8080/cliente/{id}

Descrição: Remove o cliente correspondente ao ID fornecido.

---
## Endereço

### **Obter endereço correspondente ao cliente e ao ID fornecido**

**GET:** localhost:8080/cliente/{clienteId}/endereço/{enderecoId}

**Descrição:** Retorna os detalhes do endereço correspondente ao cliente e ao ID fornecido.

---

### **Associar endereço ao cliente**

**POST:** localhost:8080/cliente/{clienteId}/endereco/associar-endereco

**Descrição:** Associa um novo endereço ao cliente.

---

### **Atualizar endereço**

**PUT:** localhost:8080/cliente/{clienteId}/endereço/{enderecoId}

**Descrição:** Atualiza os detalhes do endereço correspondente ao cliente.

---

### **Deletar endereço**

**DELETE:** localhost:8080/cliente/{clienteId}/endereço/{enderecoId}

**Descrição:** Remove o endereço correspondente ao cliente.