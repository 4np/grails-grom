FasdUAS 1.101.10   ��   ��    k             l     ��  ��    8 2 Wrapper script for sending notifications to Growl     � 	 	 d   W r a p p e r   s c r i p t   f o r   s e n d i n g   n o t i f i c a t i o n s   t o   G r o w l   
  
 l     ��  ��    , & @Author  Jeroen Wesbeek <work@osx.eu>     �   L   @ A u t h o r     J e r o e n   W e s b e e k   < w o r k @ o s x . e u >      l     ��  ��      @Since   20101102     �   $   @ S i n c e       2 0 1 0 1 1 0 2      l     ��  ��      @Description     �      @ D e s c r i p t i o n      l     ��������  ��  ��        l     ��  ��    0 * This is a wrapper applescript application     �   T   T h i s   i s   a   w r a p p e r   a p p l e s c r i p t   a p p l i c a t i o n     !   l     �� " #��   " ) # used by the Grom Plugin for Grails    # � $ $ F   u s e d   b y   t h e   G r o m   P l u g i n   f o r   G r a i l s !  % & % l     �� ' (��   ' . ( This script is copyright under the same    ( � ) ) P   T h i s   s c r i p t   i s   c o p y r i g h t   u n d e r   t h e   s a m e &  * + * l     �� , -��   , "  license as the Grom Plugin.    - � . . 8   l i c e n s e   a s   t h e   G r o m   P l u g i n . +  / 0 / l     ��������  ��  ��   0  1 2 1 i      3 4 3 I     �� 5��
�� .aevtoappnull  �   � **** 5 o      ���� 0 	arguments  ��   4 k     � 6 6  7 8 7 r      9 : 9 I     
�� ;���� 0 getparentpath getParentPath ;  <�� < I   �� =��
�� .earsffdralis        afdr =  f    ��  ��  ��   : o      ���� 0 mypath myPath 8  > ? > r     @ A @ n     B C B 4    �� D
�� 
cobj D m    ����  C o    ���� 0 	arguments   A o      ���� 0 application_name   ?  E F E r     G H G m     I I � J J    H n      K L K 1    ��
�� 
txdl L 1    ��
�� 
ascr F  M N M r    ! O P O c     Q R Q n     S T S 1    ��
�� 
rest T o    ���� 0 	arguments   R m    ��
�� 
ctxt P o      ���� 0 message   N  U V U r   " ' W X W m   " # Y Y � Z Z   X n      [ \ [ 1   $ &��
�� 
txdl \ 1   # $��
�� 
ascr V  ] ^ ] r   ( - _ ` _ J   ( + a a  b�� b m   ( ) c c � d d  G r o m��   ` l      e���� e o      ���� ,0 allnotificationslist allNotificationsList��  ��   ^  f g f r   . 3 h i h J   . 1 j j  k�� k m   . / l l � m m  G r o m��   i l      n���� n o      ���� 40 enablednotificationslist enabledNotificationsList��  ��   g  o p o l  4 4��������  ��  ��   p  q�� q O   4 � r s r k   : � t t  u v u l  : :�� w x��   w   Register with Growl...    x � y y .   R e g i s t e r   w i t h   G r o w l . . . v  z { z I  : U���� |
�� .registernull��� ��� null��   | �� } ~
�� 
appl } l 	 > A ����  m   > A � � � � �  G r o m��  ��   ~ �� � �
�� 
anot � l 
 D E ����� � o   D E���� ,0 allnotificationslist allNotificationsList��  ��   � �� � �
�� 
dnot � l 
 H I ����� � o   H I���� 40 enablednotificationslist enabledNotificationsList��  ��   � �� ���
�� 
iapp � m   L O � � � � �  S c r i p t   E d i t o r��   {  � � � l  V V��������  ��  ��   �  � � � l  V V�� � ���   �    Send notification...    � � � � ,     S e n d   n o t i f i c a t i o n . . . �  ��� � I  V ����� �
�� .notifygrnull��� ��� null��   � �� � �
�� 
name � l 	 Z ] ����� � m   Z ] � � � � �  G r o m��  ��   � �� � �
�� 
titl � b   ` e � � � l 	 ` c ����� � m   ` c � � � � �  G r a i l s   : :  ��  ��   � o   c d���� 0 application_name   � �� � �
�� 
desc � l 	 h i ����� � o   h i���� 0 message  ��  ��   � �� � �
�� 
appl � m   l o � � � � �  G r o m � �� ���
�� 
iurl � b   r { � � � b   r w � � � m   r u � � � � �  f i l e : / / � o   u v���� 0 mypath myPath � m   w z � � � � �  g r a i l s l o g o . p n g��  ��   s m   4 7 � �2                                                                                  GRRR  alis    �  Macintosh HD               ���`H+   F�GrowlHelperApp.app                                              F��]3T        ����  	                	Resources     ���P      �]4     F� F� F� F� n�  YMacintosh HD:Library:PreferencePanes:Growl.prefPane:Contents:Resources:GrowlHelperApp.app   &  G r o w l H e l p e r A p p . a p p    M a c i n t o s h   H D  LLibrary/PreferencePanes/Growl.prefPane/Contents/Resources/GrowlHelperApp.app  / ��  ��   2  � � � l     ��������  ��  ��   �  ��� � i     � � � I      �� ����� 0 getparentpath getParentPath �  ��� � o      ���� 0 thefile theFile��  ��   � O     � � � L     � � n     � � � 1   	 ��
�� 
psxp � l   	 ����� � c    	 � � � n     � � � m    ��
�� 
ctnr � o    ���� 0 thefile theFile � m    ��
�� 
ctxt��  ��   � m      � ��                                                                                  MACS  alis    r  Macintosh HD               ���`H+   n<
Finder.app                                                      ��Ƙh        ����  	                CoreServices    ���P      ƘK�     n< m� m�  3Macintosh HD:System:Library:CoreServices:Finder.app    
 F i n d e r . a p p    M a c i n t o s h   H D  &System/Library/CoreServices/Finder.app  / ��  ��       
�� � � � � � � � �����   � ����������������
�� .aevtoappnull  �   � ****�� 0 getparentpath getParentPath�� 0 mypath myPath�� 0 application_name  �� 0 message  �� ,0 allnotificationslist allNotificationsList�� 40 enablednotificationslist enabledNotificationsList��   � �� 4���� � ���
�� .aevtoappnull  �   � ****�� 0 	arguments  ��   � ���� 0 	arguments   � $���������� I���������� Y c�� l�� ��� ���~�} ��|�{�z ��y ��x ��w � ��v�u
�� .earsffdralis        afdr�� 0 getparentpath getParentPath�� 0 mypath myPath
�� 
cobj�� 0 application_name  
�� 
ascr
�� 
txdl
�� 
rest
�� 
ctxt�� 0 message  �� ,0 allnotificationslist allNotificationsList�� 40 enablednotificationslist enabledNotificationsList
�� 
appl
� 
anot
�~ 
dnot
�} 
iapp�| 
�{ .registernull��� ��� null
�z 
name
�y 
titl
�x 
desc
�w 
iurl�v 

�u .notifygrnull��� ��� null�� �*)j  k+ E�O��k/E�O���,FO��,�&E�O���,FO�kvE�O�kvE�Oa  I*a a a �a �a a a  O*a a a a �%a �a a a a  �%a !%a " #U � �t ��s�r � ��q�t 0 getparentpath getParentPath�s �p ��p  �  �o�o 0 thefile theFile�r   � �n�n 0 thefile theFile �  ��m�l�k
�m 
ctnr
�l 
ctxt
�k 
psxp�q � ��,�&�,EU � � � � ` / U s e r s / j e r o e n / W o r k s p a c e / g r a i l s / g r o m / w e b - a p p / l i b / � � � �  g s c f � � � � h S e r v e r   r u n n i n g .   B r o w s e   t o   h t t p : / / l o c a l h o s t : 8 0 8 0 / g s c f � �j ��j  �   c � �i ��i  �   l��  ascr  ��ޭ