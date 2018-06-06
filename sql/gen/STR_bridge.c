/*----------------------------------------------------------------------------
 * File:  STR_bridge.c
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  string (STR)
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "sql_sys_types.h"
#include "STRING_bridge.h"
#include "STR_bridge.h"

/*
 * Bridge:  itoa
 */
c_t *
STR_itoa( const i_t p_i )
{
  /* Replace/Insert the following instructions with your implementation code.  */
  /* IF ( ( 0 == PARAM.i ) ) */
  if ( ( 0 == p_i ) ) {
    /* RETURN 0 */
    {c_t * xtumlOALrv = "0";
    return xtumlOALrv;}
  }
  else {
    /* RETURN STRING::itoa(i:PARAM.i) */
    {c_t * xtumlOALrv = STRING_itoa( p_i );
    return xtumlOALrv;}
  }
}


/*
 * Bridge:  atoi
 */
i_t
STR_atoi( c_t * p_s )
{
  /* Replace/Insert the following instructions with your implementation code.  */
  /* RETURN STRING::atoi(s:PARAM.s) */
  {i_t xtumlOALrv = STRING_atoi( p_s );
  return xtumlOALrv;}
}


/*
 * Bridge:  substr
 */
c_t *
STR_substr( const i_t p_begin, const i_t p_end, c_t * p_s )
{
  /* Replace/Insert the following instructions with your implementation code.  */
  /* RETURN STRING::substr(begin:PARAM.begin, end:PARAM.end, s:PARAM.s) */
  {c_t * xtumlOALrv = STRING_substr( p_begin, p_end, p_s );
  return xtumlOALrv;}
}


/*
 * Bridge:  strlen
 */
i_t
STR_strlen( c_t * p_s )
{
  /* Replace/Insert the following instructions with your implementation code.  */
  /* RETURN STRING::strlen(s:PARAM.s) */
  {i_t xtumlOALrv = STRING_strlen( p_s );
  return xtumlOALrv;}
}


/*
 * Bridge:  indexof
 */
i_t
STR_indexof( c_t * p_haystack, c_t * p_needle )
{
  /* Replace/Insert the following instructions with your implementation code.  */
  /* RETURN STRING::indexof(haystack:PARAM.haystack, needle:PARAM.needle) */
  {i_t xtumlOALrv = STRING_indexof( p_haystack, p_needle );
  return xtumlOALrv;}
}


/*
 * Bridge:  getword
 */
c_t *
STR_getword( const i_t p_i, const i_t p_j, c_t * p_s )
{
  /* Replace/Insert the following instructions with your implementation code.  */
  /* RETURN STRING::getword(i:PARAM.i, j:PARAM.j, s:PARAM.s) */
  {c_t * xtumlOALrv = STRING_getword( p_i, p_j, p_s );
  return xtumlOALrv;}
}


/*
 * Bridge:  trim
 */
c_t *
STR_trim( c_t * p_s )
{
  /* Replace/Insert the following instructions with your implementation code.  */
  /* RETURN STRING::trim(s:PARAM.s) */
  {c_t * xtumlOALrv = STRING_trim( p_s );
  return xtumlOALrv;}
}


/*
 * Bridge:  quote
 * implemented as macro
 */

/*
 * Bridge:  openblockcomment
 * implemented as macro
 */

/*
 * Bridge:  closeblockcomment
 * implemented as macro
 */


/*
 * Bridge:  escapetics
 */
c_t *
STR_escapetics( c_t * p_s )
{
  /* Replace/Insert the following instructions with your implementation code.  */
  /* RETURN STRING::escapetics(s:PARAM.s) */
  {c_t * xtumlOALrv = STRING_escapetics( p_s );
  return xtumlOALrv;}
}


/*
 * Bridge:  unescapetics
 */
c_t *
STR_unescapetics( c_t * p_s )
{
  /* Replace/Insert the following instructions with your implementation code.  */
  /* RETURN STRING::unescapetics(s:PARAM.s) */
  {c_t * xtumlOALrv = STRING_unescapetics( p_s );
  return xtumlOALrv;}
}


/*
 * Bridge:  compare
 */
i_t
STR_compare( c_t * p_s1, c_t * p_s2 )
{
  c_t * s1 = p_s1;
  c_t * s2 = p_s1;
  if ( *s1 < *s2 ) return -1;
  if ( *s1 > *s2 ) return 1;
  while ( *s1 != '\0' && *s2 != '\0' ) {
    s1++; s2++;
    if ( *s1 < *s2 ) return -1;
    if ( *s1 > *s2 ) return 1;
  }
  return 0;
}

