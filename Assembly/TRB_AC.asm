
;-----------------------------------------------------------------------------
section .data					; Inicio da seccao de dados


str_angulo      db     "Introduza o Angulo: "  ;msg para exibir angulo
TAM_ANGULO      equ     $ - str_angulo	 ;tamanho da msg do angulo a exibir
						 

str_resultado   db      "Resultado = "	         ;msg para exibir resultado	
TAM_RESULTADO   equ     $ - str_resultado        ;tamanho da msg para exibir o resultado	

str_eol         db      0xA, 0xA	         ;quebra de linha	
TAM_EOL         equ     $ - str_eol

ANGULO_AUX	dq	180.0		       	

ACOT_PI		dq	1.570796327 	         ; para a formula da cotagente de PI / 2

str_funcao      db      "Introduza o operador (1- sen, 2- cos, 3- tan, 4- cotan, 5- asen, 6- acos, 7- atan, 8- acos): "
TAM_FUNCAO      equ     $ - str_funcao

;-----------------------------------------------------------------------------
; Factorial Sen

F_7		dq	5040.0

F_5		dq	120.0

F_3		dq	6.0


;-----------------------------------------------------------------------------
; Factorial Cos

F_6		dq	720.0

F_4		dq	24.0

F_2		dq	2.0

N_1		dq	1.0

;-----------------------------------------------------------------------------
; Parcela Tan

P_4		dq	0.05396825397 ;17/315

P_3		dq	0.13333333333 ;2/15

P_2		dq	0.33333333333 ;1/3	

;-----------------------------------------------------------------------------
; Parcela ASen

AS_4		dq	0.04464285714 ;5/112

AS_3		dq	0.075 	      ;3/40	

AS_2		dq	0.16666666667 ;1/6

;-----------------------------------------------------------------------------
; Parcela ACos

AC_4		dq	0.04464285714 ;5/112

AC_3		dq	0.075	      ;3/40

AC_2		dq	0.16666666667 ;1/6

AC_1		dq	1.570796327   ;PI/2

;-----------------------------------------------------------------------------
; Parcela ATan

AT_7		dq	7.0

AT_5		dq	5.0

AT_3		dq	3.0

;-----------------------------------------------------------------------------
; Variaveis para funcoes independentes

; mensagem exibida caso o BCD do resultado seja um NaN
str_nan         db      "NaN ('Not a Number': overflow ou indeterminacao)"
TAM_NAN         equ     $ - str_nan

str_menos       db      "-"
TAM_MENOS       equ     $ - str_menos

vf_cw           dw      0                       ; guarda a control-word da FPU

VF_POTENCIAS_10 dq      1E0,  1E1,  1E2,  1E3,  1E4,  1E5,  1E6,  1E7,  1E8,  1E9
                dq      1E10, 1E11, 1E12, 1E13, 1E14, 1E15, 1E16, 1E17, 1E18, 1E19
                                                ; potencias de 10 (de zero em diante) usadas
                                                ; para mover a virgula para a posicao correcta;
                                                ; so' preciso de expoentes ate' 19 porque
                                                ; o utilizador apenas pode introduzir aprox. 20
                                                ; caracteres (ver MAX_BUFFER)

VF_DIGITOS      equ     2                       ; numero de digitos exibidos apos a virgula, no resultado

tam_buffer      dd      0			;Variavel que vai guardar o tamanho do angulo lido
MAX_BUFFER      equ     80+1			;Constante que sera usada como tamanho maximo do angulo lido

;-----------------------------------------------------------------------------
section .bss				; Inicio da seccao de dados nao inicializados

buffer          resb    MAX_BUFFER		; Buffer para receber o angulo
bcd             resb    10                      ; buffer para converter de IEEE 754 com 80 bits para packed BCD (ou vice-versa)

;-----------------------------------------------------------------------------
section .text				; Inicio da seccao de codigo

                global  _start
_start:

                mov     eax, ds
                mov     es, eax

                finit                           ; inicializa a FPU: nao interromper o codigo
                                                ; em situacoes de excepcao, arredondar ao mais
                                                ; proximo, etc.

; BLOCO 1
; Pede os numeros e o operador
		

                ; mostra prompt no ecra
                mov     edx, TAM_ANGULO 	;tamanho da msg a escrever
                mov     ecx, str_angulo 	;msg a escrever
                mov     ebx, 1	      		;stdout	
                mov     eax, 4	      		;sys_write
                int     0x80	      		;chamar o linux	

                ; pede, le e converte, e coloca em st0 um numero
                call    fld_teclado
		
	
; BLOCO 2
;Pede funçoes trignometricas

  		; pede funcao
                mov     edx, TAM_FUNCAO    ;Tamanho da msg	
                mov     ecx, str_funcao	   ;Chama msg que pede as funçoes	
                mov     ebx, 1		   ;Stdout
                mov     eax, 4		   ;Sys_write	
                int     0x80  		   ;Chamar o linux			

                ; le funcao
                mov     edx, MAX_BUFFER	   ;Para sys_read, porque é a funcao que vai ler ;ecx = endereco do buffer para a leitura	
                mov     ecx, buffer	   ;Para sys_read, edx = tamanho maximo do buffer
                mov     ebx, 0		   ;Para sys_read, ebx = handle de leitura ;0 = stdin	
                mov     eax, 3		   ;Funcao do sistema (system call) ;3 = sys_read
                int     0x80		   ;Chamar linux

; BLOCO 3
; Executa as funções


		mov     dl, [buffer]   	;le o 1 caracter do operador

                cmp     dl, '1' ;cmp = compara, é igual a um sub, porem este nao guarda o resultado
                je      sen	;salta para o seno
                cmp     dl, '2'
                je      cos
                cmp     dl, '3'
                je      tan
                cmp     dl, '4'
                je      cot
                cmp     dl, '5'
                je      asen
                cmp     dl, '6'
                je      acos
		cmp     dl, '7'
                je      atan
		cmp     dl, '8'
                je      acot


		;se o operador nao corresponder, o 'resultado' sera 0
		xor	eax, eax ;quando os dois argumentos de xor sao iguais o resultado e sempre 0
		jmp	fld_teclado ;o CPU executa a instrucao dada pela label

converte_rad:
		fld    st0                 ;chama o numero introduzido      
                fldpi		           ; coloca PI na pilha
		fmulp                      ;multiplica st0 por PI	       
		fdiv	 qword[ANGULO_AUX] ;divide por 180

		ret
		
converte_graus:
		fld      st0                     
		fmul     qword[ANGULO_AUX]                 
		fldpi	       
		fdivp
			 

		ret

sen:

		call converte_rad
		;Potencia 7
		fld	st0
		fmul	st0
		fmul	st1

		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[F_7]
		fdivp	
		fxch

		

		;Potencia 5
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[F_5]
		fdivp	
		fxch
		
		

		;Potencia 3
		fld	st0
		fmul	st0
		fmul	st1
		fld	qword[F_3]
		fdivp	
		fxch
		
		fxch
		fsubp
		faddp
		fxch
		fsubp	
		
		call ecra

;--------------------------------------------	

cos:
		call converte_rad
		;Potencia 6
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[F_6]
		fdivp	
		fxch

		;Potencia 4
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fld	qword[F_4]
		fdivp	
		fxch

		;Potencia 2
		fld	st0
		fmulp	st1
		fld	qword[F_2]
		fdivp	
		
		
		fld	qword[N_1]
		fxch
		fsubp
		faddp
		fxch
		fsubp
			
		call ecra

;--------------------------------------------

tan:
		call converte_rad
		;Potencia 7
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[P_4]
		fmulp	
		fxch

		;Potencia 5
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[P_3]
		fmulp	
		fxch

		;Potencia 3
		fld	st0
		fmul	st1
		fmul	st1
		fld	qword[P_2]
		fmulp
		
		
		fxch
		faddp
		faddp
		faddp
			
		call ecra

;-------------------------------------------------
				
cot:
		call converte_rad
		;Potencia 7
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[P_4]
		fmulp	
		fxch

		;Potencia 5
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[P_3]
		fmulp	
		fxch

		;Potencia 3
		fld	st0
		fmul	st1
		fmul	st1
		fld	qword[P_2]
		fmulp
		
		
		fxch
		faddp
		faddp
		faddp
		fld	qword[N_1]
		fxch
		fdivp
			
		call ecra
				
;--------------------------------------------

asen:		
		;Potencia 7
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[AS_4]
		fmulp	
		fxch

		;Potencia 5
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[AS_3]
		fmulp	
		fxch

		;Potencia 3
		fld	st0
		fmul	st1
		fmul	st1
		fld	qword[AS_2]
		fmulp
		
		
		fxch
		faddp
		faddp
		faddp
		
		call converte_graus	
		call ecra

;--------------------------------------------

acos:
		;Potencia 7
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[AS_4]
		fmulp	
		fxch

		;Potencia 5
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[AS_3]
		fmulp	
		fxch

		;Potencia 3
		fld	st0
		fmul	st1
		fmul	st1
		fld	qword[AS_2]
		fmulp
		
		
		fxch
		faddp
		faddp
		faddp

		fld	qword[ACOT_PI]
		fxch
		fsubp
		
		call converte_graus		
		call ecra

;--------------------------------------------

atan:
		;Potencia 7
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[AT_7]
		fdivp	
		fxch

		

		;Potencia 5
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[AT_5]
		fdivp	
		fxch
		
		

		;Potencia 3
		fld	st0
		fmul	st0
		fmul	st1
		fld	qword[AT_3]
		fdivp	
		fxch
		
		fxch
		fsubp
		faddp
		fxch
		fsubp		
		
		call converte_graus		
		call ecra

;--------------------------------------------

acot:
		;Potencia 7
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[AT_7]
		fdivp	
		fxch

		

		;Potencia 5
		fld	st0
		fmul	st0
		fmul	st1
		fmul	st1
		fmul	st1
		fld	qword[AT_5]
		fdivp	
		fxch
		
		

		;Potencia 3
		fld	st0
		fmul	st0
		fmul	st1
		fld	qword[AT_3]
		fdivp	
		fxch
		
		fxch
		fsubp	
		faddp
		fxch
		fsubp

		fld	qword[ACOT_PI]
		fxch
		fsubp
		
		call converte_graus		
		call ecra


				
;--------------------------------------------


; BLOCO 5
; Escreve o resultado no ecra

ecra:
                ; exibe str_resultado
                mov     edx, TAM_RESULTADO ;tamanho da msg
                mov     ecx, str_resultado ;msg a escrever	
                mov     ebx, 1		   ;stdout
                mov     eax, 4 		   ;sys_write
                int     0x80		   ;chamar linux

                ; escreve st0 no ecra
                call    fstp_ecra	   ;chama funcao

                ; exibe str_eol (quebra de linha 'end of line')
                mov     edx, TAM_EOL	    ;tamanho da msg a escrever
                mov     ecx, str_eol	    ;msg a escrever
                mov     ebx, 1		    ;stdout
                mov     eax, 4		    ;sys_write
                int     0x80		    ;Chamar linux

                ; sair do programa
                mov     ebx, 0		   ;'Sucesso'
                mov     eax, 1		   ;sys_exit
                int     0x80		   ;chamar linux


;-----------------------------------------------------------------------------
; FUNCAO INDEPENDENTE
; Pede ao utilizador um numero decimal opcionalmente com sinal e parte
; nao-inteira (i.e., com virgula).
;
; Nao recebe argumentos e devolve em st0 o numero lido

fld_teclado:
                ; le o numero escrito pelo utilizador para "buffer"
                mov     edx, MAX_BUFFER
                mov     ecx, buffer
                mov     ebx, 0
                mov     eax, 3
                int     0x80
                dec     eax
                mov     [tam_buffer],eax

                ; converte o numero em [buffer] de string em decimal
                ; para numero BCD = Binary Code Decimal compacto
                xor     eax, eax                ; ah = digitos depois da virgula (inicialmente, 0)
                xor     edx, edx                ; vamos guardando os 16 digitos BCD em edx:ebx
                xor     ebx, ebx
                mov     word[bcd+8], 0x0000     ; inicializa os bytes nao usados de [bcd]:
                                                ; digitos 17 e 18 do BCD a zero e sinal positivo
                mov     esi, buffer             ; vamos ler a string com o numero decimal em [buffer]
                cld                             ; e vamos lendo (lodsb) da esquerda para a direita
                mov     ecx, [tam_buffer]
                jecxz   fld_teclado_fim
                
                ; testa a existencia de sinal inicial

                lodsb                           ;lodsb = carregar apartir de uma string ;le um caracter
                cmp     al, '+'                 ; compara, se o caracter e um '+'?
                je      fld_teclado_proximo     ; se for, ignora e passa ao proximo caracter
                cmp     al, '-'                 ; compara, se caracter e um '-'?
                jne     fld_teclado_sem_sinal   ; se nao for, comeca a processar este caracter normalmente
                mov     word[bcd+8], 0x8000     ; inicializa [bcd] para indicar que o numero e negativo
                jmp     fld_teclado_proximo     ; passa ao proximo caracter

fld_teclado_conv:
                lodsb                           ; le um caracter

fld_teclado_sem_sinal:
                cmp     al, '.'                 ; compara, se o caracter e uma virgula '.'?
                je      fld_teclado_virgula     ; se for, processa a virgula
                cmp     al, ','                 ; compara, se o caracter e uma virgula ','?
                jne     fld_teclado_digito      ; se nao for, deve ser um digito numerico

fld_teclado_virgula:
                mov     ah, cl                  ; se for uma virgula, guardo em ah quantos caracteres me sobram
                                                ; (estao para a direita da virgula)
                dec     ah                      ; excepto a propria virgula,
                jmp     fld_teclado_proximo     ; passa ao proximo caracter

fld_teclado_digito:				
                shl     ebx, 1                  ;shl = move todos os bits do 1 argumento tantos bits para a 
                rcl     edx, 1                  ;esquerda quanto os endicados pelo 2 argumento 
                shl     ebx, 1			;rcl = rodar a esquerda atraves da flag carry, 
                rcl     edx, 1			;parecida ao rol mas o argumento a ser rodado tem mais um bit
                shl     ebx, 1			;estas 8 instrucoes fazem um shl dem ultipla precisao: shl edx:ebx, 4
                rcl     edx, 1			
                shl     ebx, 1
                rcl     edx, 1
                and     al, 0xF                 ; converte o caracter de ASCII para BCD
                or      bl, al                  ; e combina-o no digito mais a direita de edx:ebx

fld_teclado_proximo:
                loop    fld_teclado_conv        ; repete para os restantes caracteres

fld_teclado_fim:
                mov     [bcd],   ebx            ; copia os 16 digitos BCD em edx:ebx para [bcd]
                mov     [bcd+4], edx

                ; converte o BCD compacto para virgula flutuante em st0
                fbld    [bcd]                   ; converte BCD em [bcd] para virgula flutuante em st0
                shr     eax, 8                  ; desloca ah para al de forma a que eax=ah
                fdiv    qword[VF_POTENCIAS_10 + eax*8]
                                                ; move a virgula para a posicao original
                ret


;-----------------------------------------------------------------------------
; FUNCAO INDEPENDENTE
; Escreve um numero de virgula flutuante no ecra, com sinal e parte
; nao-inteira (i.e., com virgula).
;
; Nao recebe argumentos e obtem o numero a escrever de st0, que retira da
; pilha
;---------------------

fstp_ecra:
                ; converte o numero em virgula flutuante em st0 para BCD compacto em [bcd]
                fmul    qword[VF_POTENCIAS_10 + VF_DIGITOS*8]
                                                ; move a virgula para apresentar VF_DIGITOS digitos decimais
                fbstp   [bcd]                   ; converte st0 para BCD em [bcd] e retira-o da pilha

                ; converte o numero BCD compacto em [bcd]
                ; para uma string em decimal em [buffer]
                mov     ax, [bcd+8]             ; ax = bytes nao usados de [bcd]: digitos 17 e 18 do BCD e sinal
                cmp     al, 0x00                ; verifica se os digitos 17 e 18 do BCD estao a zero
                je      fstp_ecra_sinal         ; se sim, esta' tudo normal
                
                ; exibe str_nan pois o BCD e' um NaN (ax=0xFFFF) ou tem digitos que nao conseguimos escrever!
                mov     edx, TAM_NAN
                mov     ecx, str_nan
                mov     ebx, 1
                mov     eax, 4
                int     0x80
                jmp     fstp_ecra_sair
                

fstp_ecra_sinal:
                mov     ebx, [bcd]              ; copia os 16 digitos BCD em [bcd] para edx:ebx
                mov     edx, [bcd+4]
                mov     ecx, 16                 ; 8 bytes em edx:ebx => 16 digitos decimais
                mov     edi, buffer             ; vamos guardar o numero convertido em [buffer]
                cld                             ; e vamos guardando (stosb) da esquerda para a direita
                xor     esi, esi                ; esi = OR de todos os digitos (!=0 quando for exibido o primeiro digito !=0)
                shl     ax, 1                   ; apos este shl, a flag carry (C) tem o sinal (0=positivo, 1=negativo)
                jnc     fstp_ecra_conv          ; se for positivo, nao guardar um '-'
                mov     al, '-'
                stosb                           ;stosb= guarda na string ;caso contrario, guarda o '-'

fstp_ecra_conv:
                ; se os digitos que me sobram estao "depois da virgula", guardo uma virgula nesta posicao antes deste digito
                cmp     cl, VF_DIGITOS          ; compara a quantidade de digitos que faltam (cl) com a quantidade de digitos que deveriam ser 'a direita da virgula (VF_DIGITOS)
                jne     fstp_ecra_digito        ; se nao estiver nesse ponto vou lidar normalmente com os digitos
                cmp     esi, 0                  ; ja' encontramos algum digito com algum bit a 1?
                jne     fstp_ecra_virgula       ; se sim, guarda apenas a virgula
                mov     esi, 0xF                ; se n\E3o, asseguro que todos os digitos 0 sao exibidos daqui em diante
                mov     al, '0'
                stosb                           ; e guardo um caracter '0'

fstp_ecra_virgula:
                mov     al, '.'
                stosb                           ; guardo um caracter '.'

fstp_ecra_digito:
                mov     eax, edx
                shr     eax, 32-4               ; eax = digito BCD mais 'a esquerda de edx
                or      esi, eax                ; combino (OR) este digito com todos os anteriores
                jz      fstp_ecra_ignora_0_esq  ; se ainda nenhum digito tinha nenhum bit a 1, foram todos 0 ('a esquerda): ignora este tambem
                add     al, '0'                 ; converto o digito de BCD para ASCII
                stosb                           ; e guardo-o

fstp_ecra_ignora_0_esq:
                shl     ebx, 1                  ; estas 8 instrucoes fazem um shl de
                rcl     edx, 1                  ; multipla precisao: shl edx:ebx, 4
                shl     ebx, 1
                rcl     edx, 1
                shl     ebx, 1
                rcl     edx, 1
                shl     ebx, 1
                rcl     edx, 1
                loop    fstp_ecra_conv          ; repete para os restantes digitos BCD
                sub     edi, buffer             ; calculo em edi a quantidade de caracteres (bytes) escritos
                mov     [tam_buffer], edi       ; e guardo essa quantidade em [tam_buffer]

                ; exibe o resultado
                mov     edx, [tam_buffer]
                mov     ecx, buffer
                mov     ebx, 1
                mov     eax, 4
                int     0x80

fstp_ecra_sair:
		ret
