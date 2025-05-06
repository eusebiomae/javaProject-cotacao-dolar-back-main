# 💲 Cotação Dólar - API Back-end

🚀 **Projeto Spring Boot para consulta de cotação do dólar** a partir de APIs públicas. Back-end do Projeto Cotação Dolar como teste para a empresa SHX. Oferece endpoints para:

- 📅 **Cotações em um período definido.**
- 🔄 **Cotação atual (em tempo real).**
- 🔎 **Cotações históricas menores que a atual.**

---

## 🚀 Tecnologias Utilizadas

- ☕ **Java 17**
- 🌱 **Spring Boot 3.x**
- 📦 **Maven**
- 🐱‍💻 **Tomcat 9.x**
- 🔗 **Google Gson**
- 🌐 **APIs Externas:**
  - [Banco Central do Brasil - PTAX](https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/)

---

## 📂 Estrutura do Projeto

| Arquivo/Classe      | Função                                                                                   |
|---------------------|------------------------------------------------------------------------------------------|
| `MoedaService`      | 🔧 Faz requisições HTTP às APIs externas e trata os dados recebidos.                     |
| `Moeda`             | 💲 Modelo que representa as informações de cotação (valor, data, hora).                  |
| `Periodo`           | 📅 Modelo para gerenciar intervalos de datas (usado para filtros por período).           |

---

## 🔧 Endpoints Principais

| Método | Endpoint                             | Descrição                                                     |
|--------|-------------------------------------|----------------------------------------------------------------|
| `GET`  | `/api/cotacoes/periodo`             | Busca cotações entre datas (parâmetros: `startDate`, `endDate`).|
| `GET`  | `/api/cotacoes/atual`               | Retorna a cotação atual do dólar em tempo real.                |
| `GET`  | `/api/cotacoes/menores-que-atual`   | Filtra cotações de um período que são menores que a atual.     |

⚠️ **Nota:** Verifique se os parâmetros estão corretos ao chamar os endpoints no Controller.

---

## ✅ Como Rodar o Projeto

### 1️⃣ Clone o Repositório

\`\`\`bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
\`\`\`

### 2️⃣ Importar no Eclipse (se aplicável)

1. `File > Import > Maven > Existing Maven Projects`
2. Selecione a pasta onde o projeto foi clonado.
3. Finalize a importação.

### 3️⃣ Configurar Maven (se necessário)

No terminal (ou dentro do Eclipse com `Alt + F5`):

\`\`\`bash
mvn clean install
\`\`\`

### 4️⃣ Rodar o Projeto

👉 **Pelo Eclipse:**

- Clique direito no projeto > `Run As > Spring Boot App`.

👉 **Pelo Terminal:**

\`\`\`bash
mvn spring-boot:run
\`\`\`

---

## 🔍 Testar a API

A aplicação, por padrão, estará disponível em:

\`\`\`
http://localhost:8080
\`\`\`

**Exemplos:**

- 🔗 **Buscar cotações em um período:**

\`\`\`
http://localhost:8080/api/cotacoes/periodo?startDate=2025-04-01&endDate=2025-04-15
\`\`\`

- 🔗 **Buscar cotação atual:**

\`\`\`
http://localhost:8080/api/cotacoes/atual
\`\`\`

- 🔗 **Buscar cotações menores que a atual:**

\`\`\`
http://localhost:8080/api/cotacoes/menores-que-atual?startDate=2025-04-01&endDate=2025-04-15
\`\`\`

---

## 🌐 APIs Externas Utilizadas

- [Banco Central do Brasil - PTAX](https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/)

---

## ✨ Melhorias Futuras

- 🗄️ Implementar cache para otimizar requisições externas.
- 🧪 Testes automatizados com JUnit.
- 📝 Documentação automática com Swagger/OpenAPI.

---

👤 **Autor:** *DEV. Eusebio Mario Amador Enriquez*  
🔗 **Contato:** (https://emaedev-portfolio.webflow.io/)
