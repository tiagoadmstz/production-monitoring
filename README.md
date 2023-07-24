O que precisamos?
 - dados de lançamento de produção da linha L17
 - dos dados precisamos de:
   - total de produção -> 310min
   - total de paradas não programadas -> 200min
   - total de paradas programadas -> 0min
   - total de tempo turno linha -> 510min

--------------------------------------------------------
De que forma?
 - qual a solução: exibir para o usuários os dados coletados e tratados, e gerar relatório.
 - indentificar onde estão os dados e de que forma estão armazenados. (linha x dia inteiro x turno)
	- SQL (select a.ID, a.ID_CONTROLE, a.HORA_INPUT, 		a.OBSERVACAO, a.ID_CAUSA_ID, 		a.ID_M_MATERIAL, a.VELOCIDADE, a.FLAG
		from dbo.DDZ as a
		where 1 = 1
		and CONVERT(time, a.HORA_INPUT) 
		between (select CONVERT(time, 		t.INICIO_TURNO) from dbo.Turno_Linha as t 		where t.TURNO = 'C')
		and (select CONVERT(time, t.FIM_TURNO) from 		dbo.Turno_Linha as t where t.TURNO = 'C')
		and a.ID_CONTROLE in (
			select a.ID
			from dbo.DDZ_Controle as a 
			where a.LINHA = 'L07'
			and CONVERT(date, a.DATA_BASE) = 			'2023-07-17'
		) order by a.HORA_INPUT asc;
	)
 - criar uma prova de conceito, tratando os dados de maneiras diferentes.
 - fazer um teste de caso de uso com testes unitários dentro do sistema.
 - confrontar com os dados tratados manualmente.
 - fazer o teste usando mais 4 casos diferentes.
 - buildar uma tela de visualização para o usuário.