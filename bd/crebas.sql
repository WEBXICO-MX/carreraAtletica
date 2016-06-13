/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     07/06/2016 14:57:54                          */
/*==============================================================*/


if exists (select 1
            from  sysindexes
           where  id    = object_id('PARTICIPANTES')
            and   name  = 'INDEX_1'
            and   indid > 0
            and   indid < 255)
   drop index PARTICIPANTES.INDEX_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PARTICIPANTES')
            and   type = 'U')
   drop table PARTICIPANTES
go

/*==============================================================*/
/* Table: PARTICIPANTES                                         */
/*==============================================================*/
create table PARTICIPANTES (
   ID                   smallint             not null,
   NOMBRE               varchar(50)          null,
   AP_PATERNO           varchar(50)          null,
   AP_MATERNO           varchar(50)          null,
   FECHA_NACIMIENTO     datetime             null,
   SEXO                 varchar(1)           null,
   CATEGORIA            tinyint              null,
   NUMERO_COMPETIDOR    varchar(4)           null,
   EMAIL                varchar(50)          null,
   FECHA_REGISTRO       datetime             null,
   ACTIVO               bit                  null,
   constraint PK_PARTICIPANTES primary key (ID)
)
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('PARTICIPANTES')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'CATEGORIA')
)
begin
   declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_dropextendedproperty 'MS_Description', 
   'user', @CurrentUser, 'table', 'PARTICIPANTES', 'column', 'CATEGORIA'

end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '1 =  3KM CARRERA
   2 =  3KM CAMINATA',
   'user', @CurrentUser, 'table', 'PARTICIPANTES', 'column', 'CATEGORIA'
go

/*==============================================================*/
/* Index: INDEX_1                                               */
/*==============================================================*/
create index INDEX_1 on PARTICIPANTES (
ID ASC
)
go

