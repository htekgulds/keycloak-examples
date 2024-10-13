// Please see documentation at https://learn.microsoft.com/aspnet/core/client-side/bundling-and-minification
// for details on configuring this project to bundle and minify static web assets.

// Write your JavaScript code.
$(document).ready(() => {
  $.get('/users/me', data => {
    console.log('Data', data)
    $('#user').text(JSON.stringify(data, null, 2))
  })
})