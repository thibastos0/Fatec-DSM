import streamlit as st
import pandas as pd
import plotly.express as px

# Configuração da Página
st.set_page_config(
    page_title="Dashboard de Salários na Área de Dados",
    page_icon="",
    layout="wide",
)

# Carregamento dos dados
df = pd.read_csv("https://raw.githubusercontent.com/vqrca/dashboard_slarios_dados/refs/heads/main/dados-imersao-final.csv")

# Barra Lateral (filtros)
st.sidebar.header("Filtros")

# Filtro de Ano
anos_disponiveis = sorted(df['ano'].unique())
anos_selecionados = st.sidebar.multiselect("Anos", anos_disponiveis, default=anos_disponiveis)

# Filtro de Senioridade
senioridades_disponiveis = sorted(df['senioridade'].unique())
seneriodades_selecionadas = st.sidebar.multiselect("Senioridade", senioridades_disponiveis, default=senioridades_disponiveis)

# Filtro por Tipo de Contrato
contratos_disponiveis = sorted(df['contrato'].unique())
contratos_selecionados = st.sidebar.multiselect("Tipo de Contrato", contratos_disponiveis, default=contratos_disponiveis)

# Filtro por Tamanho da Empresa
tamanhos_disponiveis = sorted(df['tamanho_empresa'].unique())
tamanhos_selecionados = st.sidebar.multiselect("Tamanho da Empresa", tamanhos_disponiveis, default=tamanhos_disponiveis)

# Filtragem do DataFrame
df_filtrando = df[
    (df['ano'].isin(anos_selecionados)) &
    (df['senioridade'].isin(seneriodades_selecionadas)) &
    (df['contrato'].isin(contratos_selecionados)) &
    (df["tamanho_empresa"].isin(tamanhos_selecionados))
]

st.title("Dashboard de Análise de Salários na Área de Dados")
st.markdown("Explore os dados salariais na área de dados nos últimos anos. Utilize os filtros à esquerda para refinar sua análise.")



