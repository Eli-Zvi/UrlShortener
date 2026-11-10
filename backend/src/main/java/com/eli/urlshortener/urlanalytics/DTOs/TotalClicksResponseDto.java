package com.eli.urlshortener.urlanalytics.DTOs;

/**
 * Dto of a response to a successful total clicks request
 * @param shortCode - the code for the url that the analytics correspond to
 * @param totalClicks - the total click events that correspond to the url
 */
public record TotalClicksResponseDto(String shortCode, Long totalClicks) {
}
